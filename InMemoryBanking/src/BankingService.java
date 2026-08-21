public class BankingService {
    private final AccountRepository accountRepository;
    private final AuditLogger auditLogger;

    public BankingService(AccountRepository accountRepository, AuditLogger auditLogger){
        this.accountRepository = accountRepository;
        this.auditLogger = auditLogger;
    }

    public SavingsAccount openSavingsAccount(String number, String holder, double initialDeposit, double rate){
        // create a savings account - minimum balance 100 dollars
        SavingsAccount sAccount = new SavingsAccount(number, holder, initialDeposit, rate, 100.0);
        // saving the account in the accountRepository
        accountRepository.save(sAccount);

        // logging the initial transaction
        Transaction[] transactions = sAccount.getTransactions();
        if (transactions.length > 0){
            auditLogger.logTransaction(transactions[0]);
        }
        return sAccount;
    }

    public CheckingAccount openCheckingAccount(String number, String holder, double initialDeposit, double overdraft){
        // create a checking account - 25 dollars maintenance fee
        CheckingAccount cAccount = new CheckingAccount(number, holder, initialDeposit, overdraft, 25.0);
        // save the account in the account repo
        accountRepository.save(cAccount);

        // logging the initial transaction
        Transaction[] transactions = cAccount.getTransactions();
        if (transactions.length > 0){
            auditLogger.logTransaction(transactions[0]);
        }
        return cAccount;
    }

    public void deposit(String accountNumber, double amount){
        if (accountNumber == null){
            throw new IllegalArgumentException("Account number cannot be null");
        }

        // finding the account via Optional
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // depositing the amount
        account.deposit(amount);

        // update the state of the account after depositing
        accountRepository.save(account);

        // logging the transaction
        Transaction[] transactions = account.getTransactions();
        Transaction latestTransaction = transactions[transactions.length - 1];
        auditLogger.logTransaction(latestTransaction);
    }

    public void withdraw(String accountNumber, double amount) throws InsufficientFundsException{
        if (accountNumber == null){
            throw new IllegalArgumentException("Account number cannot be null");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // withdraw the amount
        account.withdraw(amount);

        // update the state of the account
        accountRepository.save(account);

        // logging the transaction
        Transaction[] transactions = account.getTransactions();
        Transaction latestTransaction = transactions[transactions.length - 1];
        auditLogger.logTransaction(latestTransaction);
    }

    public void transfer(String fromAccountNum, String toAccountNum, double amount) throws InsufficientFundsException{
        // checking if the source and target destination are the same
        if (fromAccountNum == null || toAccountNum == null){
            throw new IllegalArgumentException("Account numbers cannot be null");
        }
        if(fromAccountNum.equals(toAccountNum)){
            throw new IllegalArgumentException("Cannot transfer funds to the same account.");
        }

        if (amount <= 0){
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        // fetching the source and destination accounts
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNum)
                .orElseThrow(() -> new AccountNotFoundException(fromAccountNum));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNum)
                .orElseThrow(() -> new AccountNotFoundException(toAccountNum));

        // withdraw from source
        fromAccount.withdraw(amount);

        // deposit into destination
        toAccount.deposit(amount);

        // log the transactions as TRANSFER_IN and TRANSFER_OUT in the audit logger
        Transaction transferOut = new Transaction(fromAccountNum, TransactionType.TRANSFER_OUT, amount, TransactionStatus.SUCCESS, "Transfer to " + toAccountNum);
        Transaction transferIn = new Transaction(toAccountNum, TransactionType.TRANSFER_IN, amount, TransactionStatus.SUCCESS, "Transfer from "+ fromAccountNum);

        // log the transactions
        auditLogger.logTransaction(transferOut);
        auditLogger.logTransaction(transferIn);

        // save the states of the accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public void runMonthlyMaintenance(){
       Account[] allAccounts = accountRepository.findAll();

       for(Account account : allAccounts){
           int initialTransactionsCount = account.getTransactions().length;

           // run maintenance logic on each account
           account.processMonthlyMaintenance();

           // update repository state
           accountRepository.save(account);

           // if maintenance generated a new transaction -> log it into the audit
           Transaction[] updatedTransactions = account.getTransactions();
           if(updatedTransactions.length > initialTransactionsCount){
               auditLogger.logTransaction(updatedTransactions[updatedTransactions.length - 1]);
           }
       }
    }

    public Account getAccountDetails(String accountNumber){
        if (accountNumber == null){
            throw new IllegalArgumentException("Account number cannot be null");
        }
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

    }

}
