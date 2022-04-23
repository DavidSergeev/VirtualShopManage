import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class User implements Comparable<User>{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean madePurchase;
    private ShoppingBasket basket;
    private double spended;
    private int purchaseCounter;



    public User(String firstName, String lastName, String userName, String password) {
        this.basket = new ShoppingBasket();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.madePurchase = false;
        this.purchaseCounter = 0;
        this.spended = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.matches(".*[0-9].*")) {
            System.out.println("The first name cant contain digits ");
        } else {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (firstName.matches(".*[0-9].*")) {
            System.out.println("The last name cant contain ");
        } else {
            this.lastName = lastName;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length() < 6) {
            System.out.println("Min password len 6 chars.");
        } else {
            this.password = password; }
    }

    public ShoppingBasket getBasket() {
        return basket;
    }

    public void setBasket(ShoppingBasket basket) {
        this.basket = basket;
    }

    public void addToBasket(Product currentProduct) {
        Scanner intScanner = new Scanner(System.in);
        int amount;
        try {
            do {
                System.out.println("How match units do you want? ");
                amount = intScanner.nextInt();
            }
            while (amount <= 0);
            basket.addProduct(currentProduct, amount);

        } catch (InputMismatchException e) {
            System.out.println("Unknown amount of products");
            intScanner.nextLine();
        }
    }

    public void printBasket(BaseOptions options) {
        double price;
        double totalPrice;
        double totalBasketPrice = 0;

        for (Product product : getBasket().getInBasket().keySet()) {

            int value = getBasket().getInBasket().get(product);
            if (options == BaseOptions.OPTION_1 || options == BaseOptions.OPTION_2) {
                price = product.getDiscountPrice();

            }
            else {
                price = product.getPrice();
            }
            totalPrice = price*value;
            System.out.println("Unit: " + product.getAbout() + "; unit price: " + price + "$; total units: " + value + "; total price: " + totalPrice + "$");

            totalBasketPrice+=totalPrice;
            getBasket().setTotalBasketPrice(totalBasketPrice);

        }
    }

    public boolean isMadePurchase() {
        return madePurchase;
    }

    public void setMadePurchase(boolean madePurchase) {
        this.madePurchase = madePurchase;
    }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }

    public double getSpended() {
        return spended;
    }

    public void setSpended(double spended) {
        this.spended = spended;
    }

    public int getPurchaseCounter() {
        return purchaseCounter;
    }

    public void setPurchaseCounter(int purchaseCounter) {
        this.purchaseCounter = purchaseCounter;
    }

    public int compareTo(User other) {
        double currentUserSpended = this.getSpended();
        double otherUserSpended = other.getSpended();

        return Double.compare(currentUserSpended, otherUserSpended);
    }

}
