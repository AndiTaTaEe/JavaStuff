public class BankAccount {

    //class for a customer's bank account
    static int nextAccountNumber = 1;
    private final int accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountHolderName, double initialDeposit){
        accountNumber = nextAccountNumber;
        nextAccountNumber++;
        this.accountHolderName = accountHolderName;
        if (initialDeposit >= 0.0){
            balance = initialDeposit;
        } else {
            balance = 0.0;
            System.out.println("ERROR! Negative starting balances are not allowed!");
        }
    }

    //getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    //setters
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    //deposit method
    public void deposit(double amount){
        //check if the amount is > 0
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("ERROR! You can't deposit negative amounts!");
        }
    }

    public void withdraw(double amount){
        //check if the amount > 0 and amount <= balance
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("ERROR! Your balance is smaller than the amount you want to withdraw");
        }
    }

    public void transfer(BankAccount targetAccount, double amount){
        //check if the owner's account has enough money to transfer
        if (amount <= balance && amount > 0){
            this.withdraw(amount);
            targetAccount.deposit(amount);
        } else {
            System.out.println("ERROR! Your balance is smaller than the amount you want to transfer");
        }
    }
}
