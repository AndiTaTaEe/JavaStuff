public class AccountNotFoundException extends RuntimeException {
    private final String missingAccountNumber;

    // overloaded constructor that generates a standard message automatically
    public AccountNotFoundException(String missingAccountNumber){
        super("Account not found with number: " + missingAccountNumber);
        this.missingAccountNumber = missingAccountNumber;
    }

    public AccountNotFoundException(String message, String missingAccountNumber) {
        super(message);
        this.missingAccountNumber = missingAccountNumber;
    }

    public String getMissingAccountNumber() {
        return missingAccountNumber;
    }
}
