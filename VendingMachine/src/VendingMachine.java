public class VendingMachine {
    private final Product[][] vendingGrid;
    private double balance;

    public VendingMachine(int rows, int cols){
        vendingGrid = new Product[rows][cols];
        balance = 0.0;
    }

    //loading a product into a specific row and column
    public void loadProduct(int row, int col, Product product){
        if(row>=0 && row < vendingGrid.length && col >=0 && col < vendingGrid[0].length){
            vendingGrid[row][col] = product;
        }
    }

    //inserting money
    public void insertMoney(double amount){
        if (amount > 0){
            balance += amount;
            System.out.printf("Inserted: $%.2f. Current balance: $%.2f\n", amount, balance);
        }
    }

    //purchase logic
    public void buyProduct(int row, int col){
        System.out.printf("Attempting to buy item from [" + row + "][" + col + "]\n");
        //preventing out of bounds exception
        if (row<0 || row>= vendingGrid.length || col<0 || col>= vendingGrid[0].length){
            System.out.println("Invalid selection. Slot does not exist");
            return;
        }

        Product selectedSlot = vendingGrid[row][col];

        //null check and quantity check
        if(selectedSlot == null || selectedSlot.getQuantity() == 0){
            System.out.println("Item is out of stock or empty slot!");
            return;
        }

        ProductType selectedSlotType = selectedSlot.getType();

        //checking for money
        if (balance >= selectedSlotType.getPrice()){
            balance -= selectedSlotType.getPrice();
            selectedSlot.reduceQuantity();

            System.out.println("Dispensing: " + selectedSlotType.getDisplayName());
            System.out.printf("Remained balance: %.2f\n", balance);

            //reset balance after transaction
            balance = 0.0;
        } else {
            System.out.printf("Not enough money! %s costs $%.2f. You have $%.2f.\n", selectedSlotType.getDisplayName(), selectedSlotType.getPrice(), balance);
        }
    }
}
