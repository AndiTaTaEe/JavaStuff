import java.util.Scanner;

public class BankingApp {
    private final BankingService bankingService;
    private final Scanner scanner;

    public BankingApp(BankingService bankingService){
        this.bankingService = bankingService;
        this.scanner = new Scanner(System.in);
    }
    public static void main(String[] args) {
        // wiring the dependencies
        AccountRepository repository = new InMemoryAccountRepository();
        AuditLogger logger = new AuditLogger("audit_log.csv");
        BankingService service = new BankingService(repository, logger);

        // launch the application
        BankingApp app = new BankingApp(service);
        app.start();
        }

    public void start(){
        boolean running = true;
        while(running){
            System.out.println("\n----- BANKING APPLICATION -----");
            System.out.println("1. Open Savings Account");
            System.out.println("2. Open Checking Account");
            System.out.println("3. Deposit funds");
            System.out.println("4. Withdraw funds");
            System.out.println("5. Transfer funds");
            System.out.println("6. Run monthly maintenance");
            System.out.println("7. View account details");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");


            String input = scanner.nextLine().trim();
            switch (input){
                case "1" -> handleCreateSavingsAccount();
                case "2" -> handleCreateCheckingAccount();
                case "3" -> handleDeposit();
                case "4" -> handleWithdrawal();
                case "5" -> handleTransfer();
                case "6" -> handleRunMaintenance();
                case "7" -> handleShowAccountDetails();
                case "8" -> {
                    System.out.println("Exiting banking application...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please enter a number between 1-8!");
            }
        }
    }

    private void handleCreateSavingsAccount(){
        try{
            System.out.print("Enter account number: ");
            String number = scanner.nextLine().trim();
            System.out.print("Enter account holder name: ");
            String holder = scanner.nextLine().trim();
            System.out.print("Enter initial deposit: ");
            double initialDeposit = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter interest rate (0.03 for 3%): ");
            double rate = Double.parseDouble(scanner.nextLine().trim());

            // opening the savings account
            SavingsAccount sAccount = bankingService.openSavingsAccount(number, holder, initialDeposit, rate);
            System.out.println("Savings account opened successfully for " + sAccount.getAccountHolder());
        } catch (NumberFormatException e){
            System.out.println("Error: Invalid numeric input");
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateCheckingAccount(){
        try{
            System.out.print("Enter account number: ");
            String number = scanner.nextLine().trim();
            System.out.print("Enter account holder name: ");
            String holder = scanner.nextLine().trim();
            System.out.print("Enter initial deposit: ");
            double initialDeposit = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter overdraft limit: ");
            double overdraftLimit = Double.parseDouble(scanner.nextLine().trim());

            // opening the checking account
            CheckingAccount cAccount = bankingService.openCheckingAccount(number, holder, initialDeposit, overdraftLimit);
            System.out.println("Checking account opened successfully for " + cAccount.getAccountHolder());
        } catch (NumberFormatException e){
            System.out.println("Error: Invalid numeric input");
        } catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleDeposit(){
        try {
            System.out.print("Enter account number: ");
            String number = scanner.nextLine().trim();
            System.out.print("Enter deposit amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            // making the deposit
            bankingService.deposit(number, amount);
            System.out.println("Deposit of " + amount + " $ successful.");
        } catch (AccountNotFoundException | IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleWithdrawal(){
        try {
            System.out.print("Enter account number: ");
            String number = scanner.nextLine().trim();
            System.out.print("Enter withdrawal number: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            // making the withdrawal
            bankingService.withdraw(number, amount);
            System.out.println("Withdrawal of "+ amount + " $ successful.");
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }

    private void handleTransfer(){
        try {
            System.out.print("Enter source account number: ");
            String fromAccount = scanner.nextLine().trim();
            System.out.print("Enter destination account number: ");
            String toAccount = scanner.nextLine().trim();
            System.out.print("Enter transfer amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            // making the transfer
            bankingService.transfer(fromAccount, toAccount, amount);
            System.out.println("Transferred " + amount + " $ from " + fromAccount + " to " + toAccount + " successful.");
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }

    private void handleRunMaintenance(){
        bankingService.runMonthlyMaintenance();
        System.out.println("Monthly maintenance executed across all accounts.");
    }

    private void handleShowAccountDetails(){
        try {
            System.out.print("Enter account number: ");
            String number = scanner.nextLine().trim();

            // showing account details
            Account account = bankingService.getAccountDetails(number);
            System.out.println("\n---- Account details ----");
            System.out.println("Number : " + account.getAccountNumber());
            System.out.println("Holder : " + account.getAccountHolder());
            System.out.printf("Balance: %.2f%n", account.getBalance());
            System.out.println("Transactions recorded: " + account.getTransactions().length);
        } catch (AccountNotFoundException | IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    }
