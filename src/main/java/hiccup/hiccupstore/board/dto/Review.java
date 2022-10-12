package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Setter
@Getter
@Builder
public class Review {
    private Integer boardId;
    private Integer boardTypeId;
    private Integer productId;
    private String boardContent;
    private String createDate;
    private Integer userId;
    private String userName;
    private ArrayList<String> imageNameList;
    private Integer commentCount;

}


