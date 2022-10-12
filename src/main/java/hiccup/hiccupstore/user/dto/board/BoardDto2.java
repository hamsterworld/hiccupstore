package hiccup.hiccupstore.user.dto.board;

import lombok.Data;

@Data
public class BoardDto2 {

    private Integer boardid;
    private Integer userid;
    private String boardtitle;
    private String boardcontent;
    private String imagename;

}
