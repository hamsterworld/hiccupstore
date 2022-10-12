package hiccup.hiccupstore.user.dto.order;

import lombok.Data;

@Data
public class OrderStatusChangedDto {

    private String orderstatus;
    private Integer orderid;

}
