package hiccup.hiccupstore.user.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Board1vs1UpdateForm {

    private Integer boardid;
    private String boardtitle;
    private String boardcontent;
    private String boardcategory;
    private List<String> deleteImageFiles;
    private List<MultipartFile> imageFiles;

}
