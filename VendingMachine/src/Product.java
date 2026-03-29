public class Product {
    private final ProductType type;
    private int quantity;

    public Product(ProductType type, int quantity){
        this.type = type;
        this.quantity = quantity;
    }

    public ProductType getType(){
        return type;
    }

    public int getQuantity(){
        return quantity;
    }

    //reduces stock by 1 when an item is bought
    public void reduceQuantity(){
        if(quantity>0){
            quantity--;
        }
    }
}
