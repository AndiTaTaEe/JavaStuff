public class Main {
    public static void main(String[] args) {
        // creating the machine - 2 rows 2 cols
        VendingMachine machine = new VendingMachine(2,2);

        //load products
        machine.loadProduct(0,0, new Product(ProductType.COKE, 5));
        machine.loadProduct(0,1, new Product(ProductType.CHIPS, 3));
        machine.loadProduct(1,0, new Product(ProductType.CANDY, 1));
        machine.loadProduct(1,1, new Product(ProductType.WATER,20));

        //successful purchase
        machine.insertMoney(2.00);
        machine.buyProduct(0,0); // buying coke
        System.out.println("--------------");
        //not enough money
        machine.insertMoney(0.50);
        machine.buyProduct(1,1); // water is 1.00
        System.out.println("--------------");

        //adding more money in order to complete the transaction
        machine.insertMoney(1.00);
        machine.buyProduct(1,1);
        System.out.println("---------------");

        //out of stock logic
        machine.insertMoney(10.00);
        machine.buyProduct(1,0);
        machine.insertMoney(10.00);
        machine.buyProduct(1,0);

        System.out.println("---------------");

        //empty slot
        machine.buyProduct(2,0);

    }
}