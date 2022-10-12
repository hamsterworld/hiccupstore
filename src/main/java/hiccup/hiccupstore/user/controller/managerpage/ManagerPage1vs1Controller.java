package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.managerpage.ManagerPage1vs1Service;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerPage1vs1Controller {

    private final ManagerPage1vs1Service managerPage1vs1Service;
    private final FindSecurityContext findSecurityContext;
    private final hiccup.hiccupstore.commonutil.aws.awsS3Service awsS3Service;

    /** 관리자페이지에서 1대1문의 관리하는 페이지로 이동하는 매서드입니다. */
    @GetMapping("/managerpage/managerpage1vs1")
    public String managerPage1vs1(Model model,@RequestParam(defaultValue = "1") Integer page){

        Map<String, Object> boardDtoListAndBoardTotalCountMap = managerPage1vs1Service.findUser1vs1BoardAll(page);

        Paging paging = new Paging((Integer) boardDtoListAndBoardTotalCountMap.get("boardTotalCount"), page-1, 10);

        model.addAttribute("page",page);
        model.addAttribute("paging",paging);
        model.addAttribute("BoardDtoList",
                boardDtoListAndBoardTotalCountMap.get("boardDtoList"));

        return "managerpage/managerpage1vs1";
    }


    /** 관리자페이지에서 1대1문의게시글을 보게하는 매서드입니다.*/
    @GetMapping("/managerpage/managerpage1vs1see/{boardId}")
    public String myPage1vs1See(@PathVariable Integer boardId, Model model,@RequestParam(defaultValue = "1") Integer page){

        List<User1vs1BoardDto> user1vs1BoardDtoList = managerPage1vs1Service.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredFileUrl("user1vs1image",user1vs1BoardDtoList);

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardId);
        model.addAttribute("boarddto",user1vs1BoardDtoList);
        checkIfImageIsOrNot(model,user1vs1Boards);

        return "managerpage/managerpage1vs1see";
    }


    /** 관리자페이지에서 1대1문의게시글에 답변을 다는 페이지로 이동하는 매서드입니다.*/
    @GetMapping("/managerpage/managerpage1vs1seeandanswer/{boardId}")
    public String managerPage1vs1SeeAndAnswer(@PathVariable Integer boardId, Model model,@RequestParam(defaultValue = "1") Integer page){

        List<User1vs1BoardDto> user1vs1BoardDtoList = managerPage1vs1Service.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredFileUrl("user1vs1image",user1vs1BoardDtoList);

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardId);
        model.addAttribute("boarddto",user1vs1BoardDtoList);
        checkIfImageIsOrNot(model,user1vs1Boards);

        return "managerpage/managerpage1vs1seeandanswer";

    }

    /** 관리자페이지에서 1대1문의게시글에 달은 답변을 저장하느 매서드입니다.*/
    @PostMapping("/managerpage/managerpage1vs1write")
    public String manager1vs1Write(String boardcontent,Integer boardId,Model model){

        managerPage1vs1Service.Save1vs1UserAnswer(boardId,boardcontent);
        Map<String, Object> boardDtoListAndBoardTotalCountMap = managerPage1vs1Service.findUser1vs1BoardAll(1);

        Paging paging = new Paging((Integer) boardDtoListAndBoardTotalCountMap.get("boardTotalCount"), 0, 10);

        model.addAttribute("paging",paging);
        model.addAttribute("page",1);
        model.addAttribute("BoardDtoList",boardDtoListAndBoardTotalCountMap.get("boardDtoList"));

        return "managerpage/managerpage1vs1";
    }

    private void checkIfImageIsOrNot(Model model, List<User1vs1BoardDto> user1vs1BoardDtoList) {
        if(user1vs1BoardDtoList.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
    }

}
