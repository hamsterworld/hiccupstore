package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.CommentDto;
import hiccup.hiccupstore.user.service.mypage.MyPageReviewService;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageReviewController {

    private final MyPageReviewService myPageReviewService;
    private final FindSecurityContext findSecurityContext;

    @GetMapping("/mypage/mypagereview")
    public String myPageProduct(Model model,@RequestParam(defaultValue = "1") Integer page){

        UserDto user = findSecurityContext.getUserDto();

        Map<String, Object> boardTotalCountAndBoardDtoListMap = myPageReviewService.FindBoard(page);
        Paging paging = new Paging((Integer) boardTotalCountAndBoardDtoListMap.get("boardTotalCount"), page-1, 10);

        model.addAttribute("page", page);
        model.addAttribute("boardDtoList", boardTotalCountAndBoardDtoListMap.get("boardDtoList"));
        model.addAttribute("user", user);
        model.addAttribute("paging", paging);

        return "mypage/mypagereview";

    }



    @PostMapping("/mypage/searchcomment")
    public String MyPageProductSee(@RequestParam Map<String, Object> paramMap, Model model){

        Integer boardid = Integer.valueOf(paramMap.get("boardid").toString());
        List<CommentDto> commentDtos = myPageReviewService.getComment(boardid);
        model.addAttribute("commentdtos",commentDtos);

        if(commentDtos == null || commentDtos.size() == 0){
            return "layout/mypagereviewcommentnull::#commentnull";
        }

        return "layout/mypagereviewcomment::#commentTable";

    }




}
