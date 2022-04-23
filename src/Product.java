
public class Product {
    private double price;
    private String about;
    private boolean inStock;
    private double discount;
    private double discountPrice;

    public Product(double price, String about, double discount) {
        this.price = price;
        this.about = about;
        this.discount = discount;
        this.inStock = true;
    }

    public String toString() {
        return "About product: " + about + "; price: " + price + "$" + "; discount price: " + discountPrice + "$";
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public boolean equals(Product other) {
        return this.price == other.getPrice() && this.about.equals(other.getAbout()) && this.discount == other.getDiscount();

    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
