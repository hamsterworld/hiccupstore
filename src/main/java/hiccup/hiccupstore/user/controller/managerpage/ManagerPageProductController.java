package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.managerpage.ManagerPageProductService;
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
public class ManagerPageProductController {

    private final ManagerPageProductService managerPageProductService;
    private final hiccup.hiccupstore.commonutil.aws.awsS3Service awsS3Service;

    /** 관리자페이지 상품문의게시판으로 이동하는 매서드입니다.*/
    @GetMapping("/managerpage/managerpageproduct")
    public String ManagerPage1vs1(Model model,@RequestParam(defaultValue = "1") Integer page){

        Map<String, Object> boardDtoListAndBoardTotalCountMap = managerPageProductService.findUserProductBoardAll(page);

        Paging paging = new Paging((Integer) boardDtoListAndBoardTotalCountMap.get("boardTotalCount"),
                page-1,
                10);

        model.addAttribute("page",page);
        model.addAttribute("paging",paging);
        model.addAttribute("BoardDtoList",
                boardDtoListAndBoardTotalCountMap.get("boardDtoList"));

        return "managerpage/managerpageproduct";

    }


    /** 관리자페이지 상품문의게시글을 보는 매서드입니다. */
    @GetMapping("/managerpage/managerpageproductsee/{boardId}")
    public String MyPageProductSee(@PathVariable Integer boardId, Model model,@RequestParam(defaultValue = "1") Integer page){

//        List<User1vs1BoardDto> user1vs1BoardList = managerPageProductService.SeeBoard(boardId);
//
//
//        UserDto user = findSecurityContext.getUserDto();
        List<User1vs1BoardDto> userProductBoardDtoList = managerPageProductService.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredProductFileUrl("product",userProductBoardDtoList);

        for (User1vs1BoardDto user1vs1Board : user1vs1Boards) {
            if(user1vs1Board.getImageType().equals("PRODUCT")){
                model.addAttribute("boarddto",user1vs1Board);
                break;
            }
        }

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardId);
        checkIfImageIsOrNot(model,user1vs1Boards);

        return "managerpage/managerpageproductsee";
    }

    /** 관리자페이지 상품문의게시글에 답변하는 페이지로 가는 매서드입니다.*/
    @GetMapping("/managerpage/managerpageproductseeandanswer/{boardId}")
    public String Managerpage1vs1SeeAndAnswer(@PathVariable Integer boardId, Model model,@RequestParam(defaultValue = "1") Integer page){


        //List<User1vs1BoardDto> user1vs1BoardList = managerPageProductService.SeeBoard(boardId);

        List<User1vs1BoardDto> userProductBoardDtoList = managerPageProductService.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredProductFileUrl("product",userProductBoardDtoList);

        for (User1vs1BoardDto user1vs1Board : user1vs1Boards) {
            if(user1vs1Board.getImageType().equals("PRODUCT")){
                model.addAttribute("boarddto",user1vs1Board);
                break;
            }
        }

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardId);
        checkIfImageIsOrNot(model,user1vs1Boards);

        return "managerpage/managerpageproductseeandanswer";

    }

    /** 관리자페이지 상품문의게시글에 답변한 정보를 저장하는 매서드입니다.*/
    @PostMapping("/managerpage/managerpageproductwrite")
    public String Manager1vs1Write(String boardcontent,Integer boardId,Model model){

        managerPageProductService.SaveProductUserAnswer(boardId,boardcontent);
        Map<String, Object> boardDtoListAndBoardTotalCountMap = managerPageProductService.findUserProductBoardAll(1);

        Paging paging = new Paging((Integer) boardDtoListAndBoardTotalCountMap.get("boardTotalCount"),
                0,
                10);

        model.addAttribute("page",1);
        model.addAttribute("paging",paging);
        model.addAttribute("BoardDtoList",
                (List<BoardDto>)boardDtoListAndBoardTotalCountMap.get("boardDtoList"));

        return "managerpage/managerpageproduct";
    }

    private void checkIfImageIsOrNot(Model model, List<User1vs1BoardDto> user1vs1BoardDtoList) {
        if(user1vs1BoardDtoList.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
    }

}
