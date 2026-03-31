public class Bank {
    private BankAccount[] accounts = new BankAccount[10];
    private int currentCount = 0; // for counting the accounts of the Bank

    //opening an account logic
    public void openAccount(String name, double initialDeposit){
        //checking if the customer can open an account within the Bank
        if (currentCount < accounts.length){
            accounts[currentCount] = new BankAccount(name, initialDeposit);
            System.out.println("You have successfully opened an account!");
            currentCount++;
        } else {
            System.out.println("ERROR! The bank is full.");
        }
    }

    //display all accounts in the bank; used currentCount in order to iterate only the active accounts
    public void displayAllAccounts(){
        for (int i = 0; i < currentCount; i++){
            System.out.println("ID: " + accounts[i].getAccountNumber());
            System.out.println("Name: " + accounts[i].getAccountHolderName());
            System.out.println("Balance: " + accounts[i].getBalance());
        }
    }

    public BankAccount findAccount(int id){
        for (int i = 0; i < currentCount; i++){
            if(accounts[i].getAccountNumber() == id){
                return accounts[i];
            }
        }
        return null;
    }

}
