package hiccup.hiccupstore.board.controller;

import hiccup.hiccupstore.board.dto.*;
import hiccup.hiccupstore.board.service.BoardService;
import hiccup.hiccupstore.board.util.BoardCategory;
import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final FileStore fileStore;
    private final BoardService boardService;
    private final FindSecurityContext findSecurityContext;
    // 상품, 리뷰 작성
    @GetMapping("test")
    public String index(){
        return "product/QnA";
    }
    @PostMapping("/board/productQnA/add")
    public String addProductQnA(@ModelAttribute BoardWriteForm boardWriteForm,
                                @RequestParam( value = "images",required = false) ArrayList<MultipartFile>  images) throws IOException {
        //FIXME 유저 ID를 추가해야한다.
        List<UploadFile> uploadImages = fileStore.storeFiles(images);
        ArrayList<Image> imageList = new ArrayList<>();
        for (UploadFile item : uploadImages) {
            imageList.add(Image.builder().productId(boardWriteForm.getProductId()).
                    imageName(item.getStoreFileName()).
                    imagePath(fileStore.getFullPath(item.getStoreFileName())).build());

        }
        if(uploadImages.size() == 0){
            imageList = null;
        }
        boardService.insertProductQnA(boardWriteForm.toProductQnA(boardWriteForm.getUserId()), imageList);

        return String.format("redirect:/product/detail?pid=%d",boardWriteForm.getProductId());
    }
    @PostMapping("/board/productQnA/edit")
    public String editProductQnA(@ModelAttribute BoardWriteForm boardWriteForm,
                                @RequestParam( value = "preImages", required = false) ArrayList<String> preImages,
                                @RequestParam( value = "images",required = false) ArrayList<MultipartFile>  images) throws IOException {
        //FIXME 유저 ID를 추가해야한다.
        ArrayList<Image> imageList =null;
        if(!images.get(0).getOriginalFilename().equals("")) {
            imageList = new ArrayList<>();
            List<UploadFile> uploadImages = fileStore.storeFiles(images);
            for (UploadFile item : uploadImages) {
                imageList.add(Image.builder().productId(boardWriteForm.getProductId()).
                        imageName(item.getStoreFileName()).
                        imagePath(fileStore.getFullPath(item.getStoreFileName())).build());
            }
        }
        if(preImages!= null){
            for (String image : preImages) {
                fileStore.deleteFile(fileStore.getFullPath(image));
            }
        }


        boardService.editProductQnA(boardWriteForm.toProductQnA(boardWriteForm.getUserId()), imageList);

        return String.format("redirect:/product/detail?pid=%d",boardWriteForm.getProductId());
    }
    @PostMapping("/board/productQnA/delete")
    public String deleteProductQnA( @RequestBody BoardWriteForm boardWriteForm){
        ArrayList<String> imageListName = boardService.getImageListNameByBoardId(boardWriteForm.getBoardId());
        for (String imageName: imageListName) {
            fileStore.deleteFile(fileStore.getFullPath(imageName));
        }
        boardService.deleteProductQnA(boardWriteForm.getBoardId());
        boardService.deleteImageByBoardId(boardWriteForm.getBoardId());
        return String.format("redirect:/product/detail?pid=%d",boardWriteForm.getProductId());
    }
    @GetMapping("/api/productQnAList")
    public String getProductQnAList(Model model,
                                    @RequestParam(value = "productId") Integer productId){
        model.addAttribute("productId", productId);
        model.addAttribute("productQnAList",boardService.getProductQnAByProductId(productId));
        return "product/productQna";
    }
    @GetMapping("/api/reviewList")
    public String getReviewList(Model model,
                                @RequestParam(value = "productId") Integer productId){
        model.addAttribute("productId", productId);
        model.addAttribute("reviewList",boardService.getReviewByProduct(productId));
        return "product/productReview";
    }



    @GetMapping("/api/productQnA")
    public String getProductQnA(Model model,@RequestParam("boardId")Integer boardId){
        ProductQnA productQnA = boardService.getProductQnAById(boardId);
        model.addAttribute("productQnA",productQnA);
        model.addAttribute("imageNameList",boardService.getImageListNameByBoardId(boardId));
        model.addAttribute("commentList",boardService.getCommentByBoardId(boardId));
        return "product/productQnaDetail";
    }
    @GetMapping("/api/comment")
    public String getCommentList(Model model,@RequestParam("boardId")Integer boardId){
        //FIXME 댓글로 확인하기
        model.addAttribute("boardId", boardId);
        model.addAttribute("commentList",boardService.getCommentByBoardId(boardId));
        return "product/productReviewReply";
    }
    @PostMapping("/api/comment/add")
    public String addComment(Model model,@RequestBody Comment comment){
        //FIXME user
        comment.setUserId(findSecurityContext.getUserDto().getUserId());
        boardService.insertComment(comment);
        return "redirect:/api/comment?boardId="+comment.getBoardId();
    }
    @PostMapping("/api/comment/delete")
    public String deleteComment(Model model,@RequestBody Comment comment){
        boardService.deleteCommentByCommentId(comment.getCommentId());
        return "redirect:/api/comment?boardId="+comment.getBoardId();
    }
    @PostMapping("/api/comment/edit")
    public String editComment(Model model,@RequestBody Comment comment){
        //FIXME user
        comment.setUserId(findSecurityContext.getUserDto().getUserId());
        boardService.updateComment(comment);
        return "redirect:/api/comment?boardId="+comment.getBoardId();
    }
    @PostMapping("/api/review/edit")
    public String editReview(Model model, @RequestBody BoardWriteForm boardWriteForm) {
        // FIXME userId
        // FIXME 수정해야한다.
        boardService.editReview(boardWriteForm.toReview(findSecurityContext.getUserDto().getUserId()));
        return "redirect:/api/reviewList?productId="+boardWriteForm.getProductId();
    }
    @PostMapping("/api/review/delete")
    public String deleteReview(@RequestBody BoardWriteForm boardWriteForm){
        boardService.deleteReview(boardWriteForm.getBoardId());
        return "redirect:/api/reviewList?productId="+boardWriteForm.getProductId();
    }
    @PostMapping("/api/review/add")
    public String addReview(@RequestBody BoardWriteForm boardWriteForm) {
        //FIXME userId
        boardService.insertReview(boardWriteForm.toReview(findSecurityContext.getUserDto().getUserId()));
        return "redirect:/api/reviewList?productId="+boardWriteForm.getProductId();
    }
}
