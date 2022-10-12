package hiccup.hiccupstore.user.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {

    private Integer orderid;
    private Integer userid;
    private String status;
    private String orderdate;
    private String address;

}
