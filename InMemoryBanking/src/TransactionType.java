public enum TransactionType {
    // constants with description
    DEPOSIT("Deposit") ,
    WITHDRAWAL("Withdrawal"),
    TRANSFER_IN("Transfer in"),
    TRANSFER_OUT("Transfer out"),
    INTEREST("Interest"),
    FEE("Fee");

    private final String description;

    // constructor
    private TransactionType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
