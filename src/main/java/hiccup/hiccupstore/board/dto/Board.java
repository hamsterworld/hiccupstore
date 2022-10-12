package hiccup.hiccupstore.board.dto;

import hiccup.hiccupstore.board.util.BoardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Getter
@Setter
public class Board {
    private Integer boardId;
    private Integer userId;
    private Integer boardTypeId;
    private Integer productId;
    private String boardTitle;
    private String boardContent;
    private String createDate;
    private String boardCategory;
    private String userName;
    private Integer commentCount;

    public ProductQnA toProductQnA(){
        return ProductQnA.builder().
                boardTypeId(this.boardTypeId).
                boardId(this.boardId).
                boardCategory(this.boardCategory).
                boardTypeId(BoardType.PRODUCT.getValueNum()).
                productId(this.productId).
                boardContent(this.boardContent).
                boardTitle(this.boardTitle).
                createDate(this.createDate).
                userId(this.userId).
                userName(this.userName).
                commentCount(this.commentCount).
                build();
    }
    public  Review toReview(){
        return Review.builder().
                boardId(this.boardId).
                boardTypeId(this.boardTypeId).
                productId(this.productId).
                boardContent(this.boardContent).
                createDate(this.createDate).
                userId(this.userId).
                userName(this.userName).
                commentCount(this.commentCount).
                build();
    }
}
