package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.CommentDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageReviewService {

    private final UserMapper userMapper;
    private final FindSecurityContext findSecurityContext;

    public Map<String,Object> FindBoard(Integer page) {

        HashMap<String, Object> boardTotalCountAndBoardDtoListMap = new HashMap<>();

        UserDto user = findSecurityContext.getUserDto();

        Integer boardTotalCount = userMapper.FindBoardCountByUserId(user.getUserId(),3);
        List<BoardDto> boardDtoList = userMapper.FindReviewByUserId(user.getUserId(), (page - 1) * 10, 10,3);

        boardTotalCountAndBoardDtoListMap.put("boardTotalCount",boardTotalCount);
        boardTotalCountAndBoardDtoListMap.put("boardDtoList",boardDtoList);

        return boardTotalCountAndBoardDtoListMap;

    }

    public void SeeBoard(Model model, Integer boardid) {
        //board에 상품productid도 필요하다.
        List<User1vs1BoardDto> user1vs1Boardlist = userMapper.getUserProductBoardOne(boardid);
        log.info("user1vs1BoardList = {} " ,user1vs1Boardlist);
        model.addAttribute("boarddto",user1vs1Boardlist);

    }

    public void deleteProductBoard(Integer boardid) {

        Integer integer = userMapper.deleteProductBoard(boardid);

        System.out.println("몇일까요?" + integer);

    }

    public List<CommentDto> getComment(Integer boardid) {
        List<CommentDto> comments = userMapper.getComments(boardid);
        return comments;
    }
}
