import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository{
    private static final int DEFAULT_CAPACITY = 10;
    private Account[] accounts; // storage array
    private int accountCount; // count of active stored accounts

    public InMemoryAccountRepository(){
        this.accounts = new Account[DEFAULT_CAPACITY];
        this.accountCount = 0;
    }

    private void ensureCapacity(){
        if (accountCount >= accounts.length){
            Account[] expandedAccounts = new Account[accounts.length * 2];
            System.arraycopy(accounts, 0, expandedAccounts, 0, accounts.length);
            this.accounts = expandedAccounts;
        }
    }

    @Override
    public void save(Account account) {
       if (account == null){
           throw new IllegalArgumentException("Account cannot be null");
       }

       // check for an existing account and update it
        for (int i = 0; i < accountCount; i++){
            if(accounts[i].getAccountNumber().equals(account.getAccountNumber())){
                accounts[i] = account;
                return;
            }
        }
        // adding the account at the end of the array
        ensureCapacity();
        accounts[accountCount] = account;
        accountCount++;
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        if (accountNumber == null){
            return Optional.empty();
        }

        for (int i = 0; i < accountCount; i++){
            // if an account number matches the accountNumber that we are searching -> returning the optional of that account
            if (accounts[i].getAccountNumber().equals(accountNumber)){
                return Optional.of(accounts[i]);
            }
        }
        return Optional.empty();
    }

    @Override
    public Account[] findAll() {
        Account[] copyAccounts = new Account[this.accountCount];
        System.arraycopy(accounts, 0, copyAccounts, 0, this.accountCount);
        return copyAccounts;
    }

    @Override
    public boolean deleteByAccountNumber(String accountNumber) {
        if (accountNumber == null){
            return false;
        }

        for (int i = 0; i < accountCount; i++){
            if(accounts[i].getAccountNumber().equals(accountNumber)){
                // shift all elements after i index to the left by one position
                for (int j = i; j< accountCount-1; j++){
                    accounts[j] = accounts[j+1];
                }
                accounts[accountCount-1] = null;
                accountCount--;
                return true;
            }
        }
        return false;
    }
}
