//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package hiccup.hiccupstore.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderInfo {
    private int total = 0;
    private List<OrderProductInfo> orderProductInfo = new ArrayList();

    public OrderInfo() {
    }

}
