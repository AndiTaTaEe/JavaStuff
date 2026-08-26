import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ExpenseStorage {
    private final Path expensePath;
    private static final String CSV_HEADER = "id,date,amount,category,description";

    public ExpenseStorage(String fileName){
        this.expensePath = Path.of(fileName);
        initWithHeader();
    }

    // function for creating the file and writing the header for the csv file
    private void initWithHeader(){
        try {
            if(expensePath.getParent() != null){
                Files.createDirectories(expensePath.getParent());
            }
            if (!Files.exists(expensePath) || Files.size(expensePath) == 0){
                Files.writeString(expensePath, CSV_HEADER + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to instantiate the expense log file: " + expensePath, e);
        }
    }

    // function for appending an expense to the csv file
    public void appendExpense(Expense expense) throws StorageException{
        if (expense == null){
            throw new IllegalArgumentException("Expense cannot be null");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(expensePath, StandardOpenOption.APPEND)){
            writer.write(expense.toCSVRow());
            writer.newLine();
        } catch (IOException e){
            throw new StorageException("Failed to append expense record to file: " + expensePath, e);
        }
    }

    // function for reading and parsing the data from the csv file
    public List<Expense> parsingExpenses() throws CorruptRecordException, StorageException {
        List<Expense> parsedExpenses = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(expensePath)){
            String line = reader.readLine();
            int lineNumber = 1; // for skipping the header
            while ((line = reader.readLine()) != null){
                lineNumber++;
                // skip the line if its empty
                if(line.isBlank()){
                    continue;
                }
                List<String> columns = parseCsvLine(line);
                // validate column count
                if(columns.size() != 5){
                    throw new CorruptRecordException("Expected 5 columns but found " + columns.size(), lineNumber, line);
                }
                // map the tokens into types
                try{
                    UUID id = UUID.fromString(columns.get(0).trim());
                    LocalDate date = LocalDate.parse(columns.get(1).trim());
                    double amount = Double.parseDouble(columns.get(2).trim());
                    Category category = Category.fromString(columns.get(3).trim())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + columns.get(3)));

                    String description = columns.get(4).trim();
                    // if everything is right, add the parsed expense into the list
                    parsedExpenses.add(new Expense(id, date, amount, category, description));
                } catch (DateTimeParseException | IllegalArgumentException e){
                    throw new CorruptRecordException("Data parsing failed: " + e.getMessage(), lineNumber, line, e);
                }
            }
        } catch (IOException e){
            throw new StorageException("Failed to read expenses from: " + expensePath, e);
        }
        return parsedExpenses;
    }

    private List<String> parseCsvLine(String line){
        List<String> columns = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder(); // stores chars for the field currently read
        boolean inQuotes = false; // true - if we are inside a quoted block, else - if not

        for (int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            // if we encounter a double quote character
            if (c == '\"'){
                // check for escaped quotes ("") which represents a literal quote inside quotes
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"'){
                    currentToken.append('\"'); // append a single quote
                    i++; // skip the second quote of the pair
                } else {
                    // not an escaped quote - flip the state
                    inQuotes = !inQuotes;
                }
                // encountering a comma outside the quotes
            } else if (c == ',' && !inQuotes) {
                // comma outside quotes ends the column
                columns.add(currentToken.toString()); // complete the column
                currentToken.setLength(0); // reset the buffer of the stringbuilder
            // normal chars or comma inside quotes
            } else {
                currentToken.append(c);
            }
        }
        // add the final column after loop ends
        columns.add(currentToken.toString());
        return columns;
    }

}
