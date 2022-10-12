package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.find.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FindService {

    private final UserMapper userMapper;

    public HashMap<String,Object> findUserNameByEmail(FindUserNameByEmailDto findUserNameByEmail){

        /** 닉네임과 이메일로 username 찾기*/
        String userName = userMapper.searchUserNameByEmail(findUserNameByEmail.getNickname(), findUserNameByEmail.getEmail());

        /**
         *  findUserNameByEmailCompletedMap에 { "findUserNameByEmailCompletedDto" ,Dto}를 담는다.
         *  ajax 통신으로 보내기위한 Map이다.
         * */
        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        /** 예시 username = ssoboro 이고 해당가입자의 닉네임이 오인석이라면
         *  findUserNameByEmailCompletedDto에 오인석,ssoboro를 생성자로 담아준다.
         *  그리고 생성된 dto를 위에 만들어준 Map에 담아준다.
         * */
        if(userName != null){
            FindUserNameCompletedDto findUserNameByEmailCompletedDto =
                    new FindUserNameCompletedDto(findUserNameByEmail.getNickname(), userName);
            findUserNameByEmailCompletedMap.put("findUserNameByEmailCompletedDto",findUserNameByEmailCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    /** email과 동일한 방식으로 Map을 return한다. */
    public HashMap<String,Object> findUserNameByPhone(FindUserNameByPhoneDto findUserNameByPhone){

        String username = userMapper.searchUserNameByPhone(findUserNameByPhone.getNickname(), findUserNameByPhone.getPhone());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(username != null){
            FindUserNameCompletedDto findUserNameByEmailCompletedDto = new FindUserNameCompletedDto(findUserNameByPhone.getNickname(), username);
            findUserNameByEmailCompletedMap.put("dto",findUserNameByEmailCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    /** username과 동일한 방식으로 Map을 return한다. */
    public HashMap<String,Object> findPassWordByEmail(FindPasswordByEmailDto findPasswordByEmail){

        String password = userMapper.searchPasswordByEmail(findPasswordByEmail.getUsername(), findPasswordByEmail.getEmail());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(password != null){
            FindPasswordCompletedDto findPasswordCompletedDto = new FindPasswordCompletedDto(password, findPasswordByEmail.getUsername());
            findUserNameByEmailCompletedMap.put("dto",findPasswordCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }

    /** username과 동일한 방식으로 Map을 return한다. */
    public HashMap<String,Object> findPassWordByPhone(FindPasswordByPhoneDto findPasswordByPhone){

        String password = userMapper.searchPasswordByPhone(findPasswordByPhone.getUsername(), findPasswordByPhone.getPhone());

        HashMap<String, Object> findUserNameByEmailCompletedMap = new HashMap<>();

        if(password != null){
            FindPasswordCompletedDto findPasswordCompletedDto = new FindPasswordCompletedDto(password, findPasswordByPhone.getUsername());
            findUserNameByEmailCompletedMap.put("dto",findPasswordCompletedDto);
        }

        return findUserNameByEmailCompletedMap;

    }


}
