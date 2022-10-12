package hiccup.hiccupstore.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
public class ProductDto {

    private Integer productid;
    private Integer categoryid;
    private String productname;
    @NumberFormat(pattern = "###,###")
    private Integer price;
    private Integer quantity;
    private Integer alcoholContent;
    private String brand;
    private String description;
    private String imagepath;
    private String imageName;
    private String imageType;

}
