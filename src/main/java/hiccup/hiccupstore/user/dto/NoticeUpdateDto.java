package hiccup.hiccupstore.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NoticeUpdateDto {

    private Integer noticeid;
    private String boardtitle;
    private String boardcontent;
    private String category;
    private String deleteImageFiles;
    private List<MultipartFile> imageFiles;
    private String imagename;
    private String imagepath;
    private String createdate;

}
