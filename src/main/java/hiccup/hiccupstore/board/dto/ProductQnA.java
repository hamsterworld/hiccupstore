package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class ProductQnA {
    private Integer boardId;
    private String boardCategory;
    private Integer productId;
    private Integer boardTypeId;
    private String boardContent;
    private String boardTitle;
    private String createDate;
    private Integer userId;
    private String userName;
    private ArrayList<String> imageNameList;
    private Integer commentCount;
}
