package hiccup.hiccupstore.user.dto;

import lombok.Data;

@Data
public class NoticeDto {

    private Integer noticeid;
    private String boardtitle;
    private String boardcontent;
    private String category;
    private String imagename;
    private String imagepath;
    private String createdate;

}
