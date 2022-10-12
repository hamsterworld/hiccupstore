package hiccup.hiccupstore.userpick.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class UserPick {
    private Integer productId;
    private Integer userId;
    private String productName;
    @NumberFormat(pattern = "###,###")
    private Integer price;
    private String imagePath;
}
