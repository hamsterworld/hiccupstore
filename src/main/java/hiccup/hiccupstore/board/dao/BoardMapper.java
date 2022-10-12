package hiccup.hiccupstore.board.dao;

import hiccup.hiccupstore.board.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BoardMapper {
// 일단 insert만 하면 된다.


    void insertComment(Comment comment);
    void insertReview(Review review);
    void editReview(Review review);
    void insertProductQnA(ProductQnA productQnA);
    void insertImage(ArrayList<Image> image);
    void updateComment(Comment comment);
    void editProductQnA(ProductQnA productQnA);
    void editImage(ArrayList<Image> image);

    void deleteProductQnA(Integer boardId);
    void deleteImageByBoardId(Integer boardId);
    void deleteReview(Integer boardId);
    Board getBoardById(Integer boardId);
    ArrayList<Board> getBoardByProductId(Integer productId);
    ArrayList<String> getImageListNameByBoardId(Integer boardId);
    ArrayList<Board> getBoardListByProductIdAndBoardType(@Param("productId") Integer productId,@Param("boardTypeId") Integer boardTypeId);
    Integer getBoardCountByProductIdAndBoardType (Integer productId, Integer boardTypeId);
    ArrayList<Comment> getCommentListByBoardId(Integer boardId);
    void deleteCommentByCommentId(Integer commentId);
}
