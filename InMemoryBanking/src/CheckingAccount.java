public class CheckingAccount extends Account{

    private final double overdraftLimit; // maximum allowed negative balance
    private final double maintenanceFee; // monthly service charge

    public CheckingAccount(String accountNumber, String accountHolder, double initialDeposit, double overdraftLimit, double maintenanceFee){
        super(accountNumber, accountHolder, initialDeposit);
        if (maintenanceFee < 0) {
            throw new IllegalArgumentException("Maintenance fee cannot be negative.");
        }
        if (overdraftLimit < 0){
            throw new IllegalArgumentException("Overdraft limit cannot be a negative number.");
        }
        this.overdraftLimit = overdraftLimit;
        this.maintenanceFee = maintenanceFee;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException{
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (getBalance() + overdraftLimit < amount){
            throw new InsufficientFundsException(String.format("Withdrawal denied: Exceeds overdraft limit of %.2f (Available: %.2f)", overdraftLimit, getBalance()+overdraftLimit), amount, getBalance());
        }
        adjustBalance(-amount);
        recordTransaction(new Transaction(getAccountNumber(), TransactionType.WITHDRAWAL, amount, TransactionStatus.SUCCESS, "Successful withdrawal"));
    }

    @Override
    public void processMonthlyMaintenance() {
        if (maintenanceFee > 0) {
            // if we have enough money in balance + overdraft limit, we will charge the user
            if (getBalance() + overdraftLimit >= maintenanceFee) {
                adjustBalance(-maintenanceFee);
                recordTransaction(new Transaction(getAccountNumber(), TransactionType.FEE, maintenanceFee, TransactionStatus.SUCCESS, String.format("Monthly maintenance fee deducted: %.2f", maintenanceFee)));
            } else {
                recordTransaction(new Transaction(getAccountNumber(), TransactionType.FEE, maintenanceFee, TransactionStatus.FAILED, "Insufficient funds to cover monthly maintenance fee"));
            }
        }
    }
    // getter for overdraft limit and maintenance fee
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }
}
