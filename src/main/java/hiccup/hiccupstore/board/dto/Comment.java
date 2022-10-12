package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer boardId;
    private String commentCreateDate;
    private String commentContent ;
}
