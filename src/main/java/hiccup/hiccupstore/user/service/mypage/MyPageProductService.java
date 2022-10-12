package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.BoardDto;
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
@RequiredArgsConstructor
@Slf4j
public class MyPageProductService {

    private final UserMapper userMapper;
    private final FindSecurityContext findSecurityContext;

    public Map<String, Object> FindBoard(Integer page) {

        Map<String, Object> boardDtoListAndBoardTotalCountMap = new HashMap<>();

        UserDto user = findSecurityContext.getUserDto();

        Integer boardTotalCount = userMapper.FindBoardCountByUserId(user.getUserId(),2);
        List<BoardDto> boardDtoList = userMapper.FindBoardByUserId(user.getUserId(), (page - 1) * 10, 10,2);

        boardDtoListAndBoardTotalCountMap.put("boardTotalCount",boardTotalCount);
        boardDtoListAndBoardTotalCountMap.put("boardDtoList",boardDtoList);

        return boardDtoListAndBoardTotalCountMap;

    }

    public List<User1vs1BoardDto> SeeBoard(Integer boardid) {
        //board에 상품productid도 필요하다.
        return userMapper.getUserProductBoardOne(boardid);

    }

    public void deleteProductBoard(Integer boardid) {

        Integer integer = userMapper.deleteProductBoard(boardid);

    }
}
