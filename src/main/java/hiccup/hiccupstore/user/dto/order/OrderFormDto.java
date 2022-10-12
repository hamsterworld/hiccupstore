package hiccup.hiccupstore.user.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderFormDto {

    private Integer orderId;
    private Integer userid;
    private String orderdate;
    private List<OrderLatelyProductDto> OrderLatelyProductDto = new ArrayList<>();
    private String status;

}
