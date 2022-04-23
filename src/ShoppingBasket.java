import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket {
    private Map<Product, Integer> inBasket = new HashMap<>();
    private double totalBasketPrice;

    
    public void addProduct(Product product, int amount) {
        for (Product currentProduct : inBasket.keySet()) {
           if (currentProduct != null) {
               if (currentProduct.equals(product)){
                   Integer newAmount = inBasket.get(currentProduct) + amount;
                   inBasket.put(product, newAmount);
                   return;
               }
           }
        }
        inBasket.put(product, amount);

    }


    public Map<Product, Integer> getInBasket() {
        return inBasket;
    }

    public void setInBasket(Map<Product, Integer> inBasket) {
        this.inBasket = inBasket;
    }

    public double getTotalBasketPrice() {
        return totalBasketPrice;
    }

    public void setTotalBasketPrice(double totalBasketPrice) {
        this.totalBasketPrice = totalBasketPrice;
    }
}
