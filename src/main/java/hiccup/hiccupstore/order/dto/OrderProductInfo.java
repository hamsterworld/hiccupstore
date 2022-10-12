//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package hiccup.hiccupstore.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderProductInfo {

    private String imagePath;
    private String productName;
    private int productId;
    private int quantity;
    private int price;

    public OrderProductInfo() {
    }

}
