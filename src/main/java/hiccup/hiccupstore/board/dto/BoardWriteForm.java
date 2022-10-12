package hiccup.hiccupstore.board.dto;

import hiccup.hiccupstore.board.util.BoardType;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteForm {
    private Integer boardId;
    private Integer boardTypeId;
    private Integer productId;
    private String boardCategory;
    private String boardContent;
    private String title;
    private Integer userId;

    public ProductQnA toProductQnA(Integer userId){
        return ProductQnA.builder().
                boardCategory(this.boardCategory).
                boardId(this.boardId).
                boardTypeId(BoardType.PRODUCT.getValueNum()).
                productId(this.productId).
                boardContent(this.boardContent).
                boardTitle(this.title).
                createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                userId(userId).
                build();
    }
    public  Review toReview(Integer userId){
        return Review.builder().
                boardTypeId(BoardType.REVIEW.getValueNum()).
                boardId(this.boardId).
                productId(this.productId).
                boardContent(this.boardContent).
                createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                userId(userId).
                build();
    }
}
