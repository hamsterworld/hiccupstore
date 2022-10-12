package hiccup.hiccupstore.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartForm {
    private Integer userId;
    private Integer quantity;
    private Integer productId;
}
