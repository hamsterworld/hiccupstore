package hiccup.hiccupstore.user.service.managerpage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
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
public class ManagerPageProductService {

    private final UserMapper userMapper;

    public Map<String, Object> findUserProductBoardAll(Integer page){

        Map<String, Object> boardDtoListAndBoardTotalCountMap = new HashMap<>();

        Integer boardTotalCount = userMapper.getUserProductAllCount();
        List<BoardDto> boardDtoList = userMapper.getUserProductboardall((page - 1) * 10, 10);

        boardDtoListAndBoardTotalCountMap.put("boardTotalCount",boardTotalCount);
        boardDtoListAndBoardTotalCountMap.put("boardDtoList",boardDtoList);

        return boardDtoListAndBoardTotalCountMap;
    }

    public List<User1vs1BoardDto>  SeeBoard(Integer boardid){

        return userMapper.getUserProductBoardOne(boardid);

    }

    public void SaveProductUserAnswer(Integer boardid, String BoardContent) {

        userMapper.SaveProductUserAnswer(BoardContent,boardid);

    }

}
