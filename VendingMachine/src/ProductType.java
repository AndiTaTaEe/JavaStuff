public enum ProductType {
    COKE("Coca-Cola", 1.50),
    CHIPS("Potato chips", 1.25),
    CANDY("KitKat", 1.00),
    WATER("Bottled water",1.00);

    private final String displayName;
    private final double price;

    ProductType(String displayName, double price){
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName(){
        return displayName;
    }

    public double getPrice(){
        return price;
    }
}
