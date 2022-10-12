package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.Board1vs1UpdateForm;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.mypage.MyPage1vs1Service;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPage1vs1Controller {

    private final MyPage1vs1Service myPage1vs1Service;
    private final FindSecurityContext findSecurityContext;
    private final hiccup.hiccupstore.commonutil.aws.awsS3Service awsS3Service;

    /** mypage 일대일문의게시판 들어가는 매서드 */
    @GetMapping("/mypage/mypage1vs1")
    public String MyPage1vs1(@RequestParam(defaultValue = "1") Integer page,Model model){

        Map<String, Object> boardDtoListAndBoardCountMap = myPage1vs1Service.FindBoard(page);

        Paging paging = new Paging(
                (Integer) boardDtoListAndBoardCountMap.get("boardTotalCount"),
                page-1,
                10);

        model.addAttribute("BoardDtoList",boardDtoListAndBoardCountMap.get("boardDtoList"));
        model.addAttribute("paging",paging);
        model.addAttribute("page",page);

        return "mypage/mypage1vs1";

    }

    /** mypage 일대일문의 게시판쓰기들어가는 매서드*/
    @GetMapping("/mypage/mypage1vs1write")
    public String MyPage1vs1Write(Model model){
        UserDto user = findSecurityContext.getUserDto();
        model.addAttribute("user",user);
        return "mypage/mypage1vs1write";
    }

    /** mypage 일대일문의 게시글 저장하는 매서드*/
    @PostMapping("/mypage/mypage1vs1write")
    public String MyPage1vs1WritePost(@ModelAttribute Board1vs1Form board1vs1Form,Model model) throws IOException {

        List<UploadFile> storedFileNames = awsS3Service.uploadImage("user1vs1image",board1vs1Form.getImageFiles());
        myPage1vs1Service.SaveBoard1vs1(board1vs1Form,storedFileNames);

        Map<String, Object> boardDtoListAndBoardCountMap = myPage1vs1Service.FindBoard(1);

        Paging paging = new Paging((Integer) boardDtoListAndBoardCountMap.get("boardTotalCount"), 0, 10);

        model.addAttribute("BoardDtoList",boardDtoListAndBoardCountMap.get("boardDtoList"));
        model.addAttribute("paging",paging);

        return "mypage/mypage1vs1";

    }


    /** mypage 일대일문의 게시글보기  매서드*/
    @GetMapping("/mypage/mypage1vs1see/{boardId}")
    public String MyPage1vs1See(@PathVariable Integer boardId,
                                @RequestParam(defaultValue = "1") Integer page,Model model){

        UserDto user = findSecurityContext.getUserDto();
        List<User1vs1BoardDto> user1vs1BoardDtoList = myPage1vs1Service.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredFileUrl("user1vs1image",user1vs1BoardDtoList);

        model.addAttribute("page",page);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("user",user);
        model.addAttribute("boardid",boardId);
        model.addAttribute("boarddto",user1vs1Boards);
        checkIfImageIsOrNot(model, user1vs1BoardDtoList);

        return "mypage/mypage1vs1see";
    }

    @GetMapping("/mypage/mypage1vs1update/{boardId}")
    public String MyPage1vs1Update(@PathVariable Integer boardId, Model model){

        UserDto user = findSecurityContext.getUserDto();
        List<User1vs1BoardDto> user1vs1BoardDtoList = myPage1vs1Service.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredFileUrl("user1vs1image",user1vs1BoardDtoList);

        model.addAttribute("page",1);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("boardid",boardId);
        model.addAttribute("boarddto",user1vs1Boards);
        checkIfImageIsOrNot(model, user1vs1BoardDtoList);

        return "mypage/mypage1vs1update";
    }

    @PostMapping("/mypage/mypage1vs1updatepost/{boardId}")
    public String MyPage1vs1UpdatePost(@ModelAttribute Board1vs1UpdateForm boardimageUpdateForm) throws IOException {

        List<UploadFile> storedFileNames = awsS3Service.uploadImage("user1vs1image",boardimageUpdateForm.getImageFiles());

        if(boardimageUpdateForm.getDeleteImageFiles() != null){
            for (String deleteImageFileName : boardimageUpdateForm.getDeleteImageFiles()) {
                awsS3Service.deleteFile(deleteImageFileName,"user1vs1image");
            }
        }
        myPage1vs1Service.UpdateBoard1vs1Form(boardimageUpdateForm,
                storedFileNames,
                boardimageUpdateForm.getBoardid());

        return "redirect:/mypage/mypage1vs1";
    }

//    @ResponseBody
//    @GetMapping("/testimage/{filename}")
//    public Resource downLoadImage(@PathVariable String filename) throws MalformedURLException {
//
//        return new UrlResource("file:"+fileStore.getFullPath(filename));
//
//    }

    private void checkIfImageIsOrNot(Model model, List<User1vs1BoardDto> user1vs1BoardDtoList) {
        if(user1vs1BoardDtoList.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
    }

}
