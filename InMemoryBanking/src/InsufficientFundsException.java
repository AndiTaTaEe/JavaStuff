public class InsufficientFundsException extends Exception {
    private final double attemptedAmount;
    private final double currentBalance;
    public InsufficientFundsException(String message, double attemptedAmount, double currentBalance) {
        super(message);
        this.attemptedAmount = attemptedAmount;
        this.currentBalance = currentBalance;
    }

    public double getAttemptedAmount() {
        return attemptedAmount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
}
