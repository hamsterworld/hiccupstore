package hiccup.hiccupstore.user.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardimageUpdateForm {

    private List<String> deleteImageFiles;
    private List<MultipartFile> imageFiles;
    private Integer boardid;

}
