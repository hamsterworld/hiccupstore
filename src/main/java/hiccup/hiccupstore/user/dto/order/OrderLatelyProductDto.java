package hiccup.hiccupstore.user.dto.order;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class OrderLatelyProductDto {

    private Integer orderid;
    private String orderdate;
    private Integer productid;
    private String productname;
    @NumberFormat(pattern = "###,###")
    private Integer price;
    private Integer quantity;
    private String status;
    private String imagepath;

}
