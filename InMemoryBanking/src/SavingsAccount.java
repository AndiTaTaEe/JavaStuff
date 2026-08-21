public class SavingsAccount extends Account{
    private final double annualInterestRate; // percentage rate annual
    private final double minimumBalance; // mandatory threshold required to avoid penalties

    public SavingsAccount(String accountNumber, String accountHolder, double initialDeposit, double annualInterestRate, double minimumBalance){
        super(accountNumber, accountHolder, initialDeposit);

        if (annualInterestRate < 0){
            throw new IllegalArgumentException("Annual interest rate cannot be negative.");
        }

        if (minimumBalance < 0){
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }

        if (initialDeposit < minimumBalance){
            throw new IllegalArgumentException("Initial deposit cannot be less than the minimum balance.");
        }

        this.annualInterestRate = annualInterestRate;
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        // verify if the balance drops below minimum balance
        if (getBalance() - amount < minimumBalance){
            throw new InsufficientFundsException("Withdrawal denied: Balance cannot fall below minimum balance of " + minimumBalance, amount, getBalance());
        }

        // withdraw the money
        adjustBalance(-amount);

        // record the transaction
        recordTransaction(new Transaction(getAccountNumber(), TransactionType.WITHDRAWAL, amount, TransactionStatus.SUCCESS, "Successful withdrawal"));
    }

    @Override
    public void processMonthlyMaintenance() {
        // calculating the monthly interest amount - balance * (annual interest rate / 12)
        double monthlyInterestAmount = getBalance() * (annualInterestRate/12);
        if (monthlyInterestAmount > 0){
            // adjusting the balance with the interest amount if > 0
            adjustBalance(monthlyInterestAmount);
            recordTransaction(new Transaction(getAccountNumber(), TransactionType.INTEREST, monthlyInterestAmount, TransactionStatus.SUCCESS, String.format("Monthly interest applied: .%2f", monthlyInterestAmount)));
        }
    }

    // getters
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }
    public double getMinimumBalance() {
        return minimumBalance;
    }
}
