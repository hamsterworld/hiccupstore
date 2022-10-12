package hiccup.hiccupstore.user.dto.board;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class User1vs1BoardDto {

    private Integer userid;
    private String boardtitle;
    private String boardcontent;
    private String boardcategory;
    private String createdate;
    private Integer commentid;
    private String commentcontent;
    private String commentcreatedate;
    private Integer imageid;
    private String imagename;
    private String imagepath;
    private String productname;
    @NumberFormat(pattern = "###,###")
    private Integer price;
    private String nickname;
    private Integer productId;
    private String s3imageUrl;
    private String imageType;

}
