import java.util.Objects;

public abstract class Account {
    private final String accountNumber; // unique account id
    private final String accountHolder; // owner's name
    private double balance; // current account balance
    private Transaction[] transactions; // array holding history entries
    private int transactionCount; // no of valid items in the array
    private static final int DEFAULT_CAPACITY = 10; // inital capacity of the array


    public Account(String accountNumber, String accountHolder, double initialDeposit) {
        if(initialDeposit < 0){
            throw new IllegalArgumentException("Cannot pass a negative starting balance.");
        }
        // non null identifiers verifications
        Objects.requireNonNull(accountNumber, "Account number cannot be null");
        Objects.requireNonNull(accountHolder, "Account holder cannot be null");
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;

        // initializing internal array
        this.transactions = new Transaction[DEFAULT_CAPACITY];
        this.transactionCount = 0;

        // record opening deposit if initial deposit > 0
        if(initialDeposit > 0){
            recordTransaction(new Transaction(
                    accountNumber,
                    TransactionType.DEPOSIT,
                    initialDeposit,
                    TransactionStatus.SUCCESS,
                    "Initial account opening deposit"
            ));
        }
    }

    public void deposit(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than 0.");
        }
        // adding the amount with the balance
        this.balance += amount;
        // creating a new record for this transaction
        recordTransaction(new Transaction(accountNumber, TransactionType.DEPOSIT, amount, TransactionStatus.SUCCESS, "Successful deposit"));
    }

    // custom withdrawal contract
    public abstract void withdraw(double amount) throws InsufficientFundsException;

    // custom recurring cycle logic
    public abstract void processMonthlyMaintenance();

    // internal mutator for derived classes (CheckingAccount, SavingsAccount)
    protected void adjustBalance(double amount){
        this.balance += amount;
    }

    // function for duplicating and expanding the array when the transaction array is full
    private void ensureCapacity(){
        if(transactionCount >= transactions.length){
            Transaction[] expandedTransactions = new Transaction[transactions.length * 2];
            System.arraycopy(transactions, 0, expandedTransactions, 0, transactions.length);
            this.transactions = expandedTransactions;
        }
    }

    // recording a transaction
    protected void recordTransaction(Transaction t){
        ensureCapacity(); // verifying if we have the capacity to record the transaction
        this.transactions[transactionCount] = t;
        this.transactionCount++;
    }

    // getters
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolder(){
        return accountHolder;
    }
    public double getBalance() {
        return balance;
    }
    // returns a sized copy of the array containing only existing transactions
    public Transaction[] getTransactions() {
        Transaction[] copyTransactions = new Transaction[this.transactionCount];
        System.arraycopy(transactions, 0 , copyTransactions, 0, this.transactionCount);
        return copyTransactions;
    }
}
