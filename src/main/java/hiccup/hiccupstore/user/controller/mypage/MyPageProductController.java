package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.mypage.MyPageProductService;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageProductController {

    private final MyPageProductService myPageProductService;
    private final FindSecurityContext findSecurityContext;
    private final hiccup.hiccupstore.commonutil.aws.awsS3Service awsS3Service;

    /** mypage에서 상품문의 게시판으로 가게하는 매서드입니다. */
    @GetMapping("/mypage/mypageproduct")
    public String myPageProduct(Model model,@RequestParam(defaultValue = "1") Integer page){

        Map<String, Object> boardDtoListAndBoardTotalCountMap = myPageProductService.FindBoard(page);

        Paging paging = new Paging((Integer) boardDtoListAndBoardTotalCountMap.get("boardTotalCount"),
                page-1, 10);


        model.addAttribute("page",page);
        model.addAttribute("BoardDtoList",
                boardDtoListAndBoardTotalCountMap.get("boardDtoList"));
        model.addAttribute("user",findSecurityContext.getUserDto());
        model.addAttribute("paging",paging);

        return "mypage/mypageproduct";

    }

    /** 마이페이지에서 상품문의게시글을 보는 매서드입니다. */
    @GetMapping("/mypage/mypageproductsee/{boardId}")
    public String MyPageProductSee(@PathVariable Integer boardId, Model model,@RequestParam(defaultValue = "1") Integer page){

        UserDto user = findSecurityContext.getUserDto();
        List<User1vs1BoardDto> userProductBoardDtoList = myPageProductService.SeeBoard(boardId);
        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredProductFileUrl("product",userProductBoardDtoList);

        for (User1vs1BoardDto user1vs1Board : user1vs1Boards) {
            if(user1vs1Board.getImageType().equals("PRODUCT")){
                model.addAttribute("boarddto",user1vs1Board);
                break;
            }
        }
        model.addAttribute("page",page);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("boardid",boardId);

        return "mypage/mypageproductsee";
    }

    /** 마이페이지에서 상품문의게시글을 지우는 매서드입니다.*/
    @PostMapping("/mypage/mypageproductdelete")
    public String MyPageProductDelete(Integer boardId, Model model){

        myPageProductService.deleteProductBoard(boardId);
        Map<String, Object> boardDtoListAndBoardTotalCountMap = myPageProductService.FindBoard(1);

        Paging paging = new Paging((Integer) boardDtoListAndBoardTotalCountMap.get("boardTotalCount"),
                0, 10);

        model.addAttribute("page",1);
        model.addAttribute("confirm","DEL_OK");
        model.addAttribute("BoardDtoList",
                (List<BoardDto>)boardDtoListAndBoardTotalCountMap.get("boardDtoList"));
        model.addAttribute("user",findSecurityContext.getUserDto());
        model.addAttribute("paging",paging);

        return "mypage/mypageproduct";
    }



}
