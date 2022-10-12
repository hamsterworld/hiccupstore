package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.LoginUserForm;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserMapper userMapper;

    public UserDto getUser(LoginUserForm loginUserForm, BindingResult bindingResult){

        UserDto user = userMapper.getUser(loginUserForm.getUserName());

        if(user == null){

            log.info("아이디가 없음");
            bindingResult.reject("아이디가 없음","아이디를 찾을 수 없습니다.");

            return null;

        } else if(!user.getPassword().equals(loginUserForm.getPassword())){

            log.info("비밀번호가 안맞음");
            bindingResult.reject("비밀번호가 맞지않음","비밀번호가 맞지 않습니다.");

            return null;
        }

        return user;

    }

}
