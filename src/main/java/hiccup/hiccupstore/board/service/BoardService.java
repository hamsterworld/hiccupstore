package hiccup.hiccupstore.board.service;

import hiccup.hiccupstore.board.dao.BoardMapper;
import hiccup.hiccupstore.board.dto.*;
import hiccup.hiccupstore.board.util.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public void insertReview(Review review){
        boardMapper.insertReview(review);
    }
    @Transactional
    public void insertProductQnA(ProductQnA productQnA, ArrayList<Image> imageList){

        boardMapper.insertProductQnA(productQnA);
        if(imageList !=null){
            for (Image image:imageList) {
                image.setBoardId(productQnA.getBoardId());
            }
            boardMapper.insertImage(imageList);
        }
    }

    @Transactional
    public void editProductQnA(ProductQnA productQnA, ArrayList<Image> imageList){
        if(imageList != null){
            for (Image image:imageList) {
                image.setBoardId(productQnA.getBoardId());
            }
            boardMapper.deleteImageByBoardId(productQnA.getBoardId());
            boardMapper.insertImage(imageList);
        }
        else{
            boardMapper.deleteImageByBoardId(productQnA.getBoardId());
        }
        boardMapper.editProductQnA(productQnA);

    }
    @Transactional
    public void editReview(Review review){
        boardMapper.editReview(review);

    }
    public void deleteProductQnA(Integer boardId){
        boardMapper.deleteProductQnA(boardId);
    }
    public void deleteImageByBoardId(Integer boardId){
        boardMapper.deleteImageByBoardId(boardId);
    }
    public ProductQnA getProductQnAById(Integer boardId){
        return boardMapper.getBoardById(boardId).toProductQnA();
    }
    public Review getReviewById(Integer boardId){
        return boardMapper.getBoardById(boardId).toReview();
    }
    public ArrayList<String> getImageListNameByBoardId(Integer boardId){
        ArrayList<String> imageNameList = boardMapper.getImageListNameByBoardId(boardId);
        if (imageNameList == null){
            return new ArrayList<String>();
        }
        return imageNameList;
    }
    public ArrayList<ProductQnA> getProductQnAByProductId(Integer productId){
        ArrayList<Board> boardList = boardMapper.getBoardListByProductIdAndBoardType(productId, BoardType.PRODUCT.getValueNum());
        ArrayList<ProductQnA> productQnAList = new ArrayList<>();
        for (Board board: boardList) {
            productQnAList.add(board.toProductQnA());
        }
        return productQnAList;
    }
    public ArrayList<Review> getReviewByProduct(Integer productId){
        ArrayList<Board> boardList = boardMapper.getBoardListByProductIdAndBoardType(productId, BoardType.REVIEW.getValueNum());
        ArrayList<Review> reviewList = new ArrayList<>();
        for (Board board: boardList) {
            Review review = board.toReview();

            review.setImageNameList(boardMapper.getImageListNameByBoardId(board.getBoardId()));

            reviewList.add(review);
        }
        return reviewList;
    }
    public void deleteReview(Integer boardId){
        boardMapper.deleteReview(boardId);
    }
    public Integer getBoardCountByProductIdAndBoardType (Integer productId, Integer boardTypeId){
        //FIXME pagenation 구현용
        return boardMapper.getBoardCountByProductIdAndBoardType(productId, boardTypeId);
    }
    public ArrayList<Comment> getCommentByBoardId(Integer boardId){
        return boardMapper.getCommentListByBoardId(boardId);
    }
    public void deleteCommentByCommentId(Integer commentId){
        boardMapper.deleteCommentByCommentId(commentId);
    }
    public void insertComment(Comment comment){
        boardMapper.insertComment(comment);
    }
    public void updateComment(Comment comment){
        boardMapper.updateComment(comment);
    }
}
