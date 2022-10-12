package hiccup.hiccupstore.user.dto.order;

import lombok.Data;

@Data
public class OrderProductDto {

    private Integer orderproductid;
    private Integer productid;
    private Integer orderid;
    private Integer orderprice;
    private Integer quantity;

}
