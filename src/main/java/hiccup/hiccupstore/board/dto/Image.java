package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Image {
    private  Integer productId;
    private Integer imageId;
    private Integer boardId;
    private String imageName;
    private String imagePath;
}
