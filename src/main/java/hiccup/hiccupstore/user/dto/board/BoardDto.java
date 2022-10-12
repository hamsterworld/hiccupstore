package hiccup.hiccupstore.user.dto.board;

import lombok.Data;

@Data
public class BoardDto {

    private Integer boardid;
    private Integer userid;
    private String boardcategory;
    private Integer boardtypeid;
    private String boardtitle;
    private String boardcontent;
    private String createdate;
    private Integer commentid;
    private Integer productid;
    private String nickname;

}
