import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AuditLogger {
    private static final String CSV_HEADER = "id,accountNumber,type,amount,timestamp,status,description";
    private final Path logFilePath; // target destination for .csv or .log file

    public AuditLogger(String fileName){
        this.logFilePath = Path.of(fileName);
        initFileWithHeader();
    }

    // helper function for initiating the file and creating the header
    private void initFileWithHeader(){
        try {
            // create parent directories if they doesnt exist
            if (logFilePath.getParent() != null){
                Files.createDirectories(logFilePath.getParent());
            }
            // write the header if the file doesnt exist or its completely empty
            if (!Files.exists(logFilePath) || Files.size(logFilePath) == 0){
                Files.writeString(logFilePath, CSV_HEADER + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
        } catch (IOException e){
            throw new RuntimeException("Failed to initialize audit log file: " + logFilePath, e);
        }
    }

    // helper function for logging the transaction
    public void logTransaction(Transaction transaction){
        if (transaction == null){
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        try{
            // using the csvtorow helper function from transaction
            String csvLine = transaction.toCSVRow() + System.lineSeparator();
            Files.writeString(logFilePath, csvLine, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to log the transaction: " + transaction.id(), e);
        }
    }

    public void exportAccountSummary(Account account){
        // check if the account is a null object or not
        if (account == null){
            throw new IllegalArgumentException("Account cannot be null");
        }

        // creating the export path
        Path exportPath = Path.of("summary_" + account.getAccountNumber() + ".txt");
        StringBuilder sb = new StringBuilder();

        // appending the text for the summary
        sb.append("------------------\n");
        sb.append("ACCOUNT SUMMARY REPORT");
        sb.append("------------------\n");
        sb.append("Account number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Account holder : ").append(account.getAccountHolder()).append("\n");
        sb.append(String.format("Current balance: %.2f\n", account.getBalance()));
        sb.append("------------------\n");
        sb.append("TRANSACTION HISTORY:\n");

        // getting the transactions of the account
        Transaction[] historyTransactions = account.getTransactions();
        if(historyTransactions.length == 0){
            sb.append("No transactions recorded.\n");
        } else {
            for (Transaction t : historyTransactions){
                // appending all of the transactions
                sb.append(String.format("[%s] %-10s %10.2f | %-7s | %s\n", t.timestamp(), t.type(), t.amount(), t.status(), t.description()));
            }
        }
        sb.append("------------------\n");
        sb.append("Total Transactions: ").append(historyTransactions.length).append("\n");
        try {
            // writing the content of the string builder to the export path
            Files.writeString(exportPath, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export summary report for account: " + account.getAccountNumber(), e);
        }


    }
}
