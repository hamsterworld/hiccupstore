package hiccup.hiccupstore.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderProduct {

    private int orderProductId;
    private int productId;
    private int orderId;
    private int orderPrice;
    private int quantity;

    public OrderProduct() {
    }

    public OrderProduct(int orderProductId, int productId, int orderId, int orderPrice, int quantity) {
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }
}
