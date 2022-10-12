package hiccup.hiccupstore.user.service.managerpage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerPage1vs1Service {

    private final UserMapper userMapper;

    public Map<String,Object> findUser1vs1BoardAll(Integer page){

        Map<String, Object> boardDtoListAndBoardTotalCountMap = new HashMap<>();

        Integer boardTotalCount = userMapper.getUser1vs1AllCount();
        List<BoardDto> boardDtoList = userMapper.getUser1vs1boardall((page - 1) * 10, 10);

        boardDtoListAndBoardTotalCountMap.put("boardTotalCount",boardTotalCount);
        boardDtoListAndBoardTotalCountMap.put("boardDtoList",boardDtoList);

        return boardDtoListAndBoardTotalCountMap;

    }

    public List<User1vs1BoardDto> SeeBoard(Integer boardid){

        return userMapper.getUser1vs1BoardOne(boardid);

    }

    public void Save1vs1UserAnswer(Integer boardId, String BoardContent) {

        userMapper.Save1vs1UserAnswer(BoardContent,boardId);

    }

}
