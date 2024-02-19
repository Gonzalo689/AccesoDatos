import com.google.gson.annotations.SerializedName;

public class Expense {
    @SerializedName("WHAT")
    private String product;
    @SerializedName("AMOUNT")
    private double price;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Expense [product=" + product + ", price =" + price + "]";
    }

    
}
