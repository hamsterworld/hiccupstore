package hiccup.hiccupstore.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private int userId;
    private int productId;
    private int quantity;
    private String  productName;
    private int price;
    private String imagePath;
    private int productQuantity;

}

