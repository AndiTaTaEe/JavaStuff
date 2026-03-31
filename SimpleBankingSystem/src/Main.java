public class Main {
    public static void main(String[] args) {
        Bank bank1 = new Bank();
        bank1.openAccount("Andrew", 25.00);
        bank1.openAccount("John", -5.00);
        bank1.openAccount("David", 30.00);
        System.out.println("--------------");
        System.out.println("BANK ACCOUNTS AT THE OPENING");
        bank1.displayAllAccounts();
        System.out.println("--------------");

        // saving the bankAccount objects - need it for the transfer
        BankAccount sender = bank1.findAccount(1);
        BankAccount receiver = bank1.findAccount(99);

        sender.deposit(20.00);
        //sender.withdraw(50.00); // should display an error
        sender.withdraw(15.00);

        //transfer logic
        if (receiver != null){
            sender.transfer(receiver, 10.00);
        } else {
            System.out.println("ERROR! The Receiver account doesn't exist in the bank. Try with another account!");
        }
        System.out.println("--------------");
        System.out.println("BANK ACCOUNTS AFTER THE TRANSACTIONS");
        bank1.displayAllAccounts();

    }
}