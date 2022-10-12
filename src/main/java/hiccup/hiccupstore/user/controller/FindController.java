package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.find.FindPasswordByEmailDto;
import hiccup.hiccupstore.user.dto.find.FindPasswordByPhoneDto;
import hiccup.hiccupstore.user.dto.find.FindUserNameByEmailDto;
import hiccup.hiccupstore.user.dto.find.FindUserNameByPhoneDto;
import hiccup.hiccupstore.user.service.FindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    /** 아이디찾기 */
    @GetMapping("/find/findusername")
    public String findUserName(){

        return "login/findusername";

    }

    /** 이메일로 아이디 찾기 */
    @PostMapping("/find/findusernamebyemail")
    @ResponseBody
    public HashMap<String,Object> postFindUserNameByEmail(@RequestBody FindUserNameByEmailDto findUserNameByEmailDto){

        return findService.findUserNameByEmail(findUserNameByEmailDto);

    }

    /** 폰번호로 아이디 찾기 */
    @PostMapping("/find/findusernamebyphone")
    @ResponseBody
    public HashMap<String,Object> postFindUserNameByPhone(@RequestBody FindUserNameByPhoneDto findUserNameByPhoneDto){

        return findService.findUserNameByPhone(findUserNameByPhoneDto);

    }

    /** 비밀번호 찾기*/
    @GetMapping("/find/findpassword")
    public String findpassword(){

        return "login/findpassword";

    }

    /** 이메일로 비밀번호 찾기 */
    @PostMapping("/find/findpasswordbyemail")
    @ResponseBody
    public HashMap<String,Object> postFindPasswordByEmail(@RequestBody FindPasswordByEmailDto findPasswordByEmailDto){

        return findService.findPassWordByEmail(findPasswordByEmailDto);

    }

    /** 폰번호로 비밀번호 찾기 */
    @PostMapping("/find/findpasswordbyphone")
    @ResponseBody
    public HashMap<String,Object> postFindPasswordByPhone(@RequestBody FindPasswordByPhoneDto findPasswordByPhoneDto){

        return findService.findPassWordByPhone(findPasswordByPhoneDto);

    }

}
