package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.join.DuplicateUserNameDto;
import hiccup.hiccupstore.user.dto.join.JoinFormDto;
import hiccup.hiccupstore.user.dto.join.SnsJoinDto;
import hiccup.hiccupstore.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final JoinService joinservice;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String join(){
        return "register/register";
    }

    @GetMapping("/join/joinform")
    public String joinForm(@ModelAttribute JoinFormDto joinFormDto){
        return "register/registerinput";
    }


    /** 회원가입하는 회원들의 정보 저장하는 매서드 */
    @PostMapping("/join/joincomplete")
    public String joinComplete(@Validated @ModelAttribute JoinFormDto joinFormDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        /** 글로벌 오류 비밀번호 == 비밀번호확인 */
        if(!joinFormDto.getPassword().equals(joinFormDto.getPasswordConfirm())){
            bindingResult.reject("NotMatchedPassWord",null,"비밀번호를 일치시켜주세요.");
        }

        /** 필드오류  유효성조건은 JoinFormDto에서 확인하세요. */
        if(bindingResult.hasErrors()){
            return "register/registerinput";
        }

        /** 비밀번호 암호화 하는 과정*/
        joinFormDto.setPassword(passwordEncoder.encode(joinFormDto.getPassword()));
        joinservice.userSave(joinFormDto);

        redirectAttributes.addFlashAttribute("nickname",joinFormDto.getNickName());

        return "redirect:/join/registercomplete";

    }

    /** 회원가입이 완료됬다는 것을 보여주는 매서드입니다.*/
    @GetMapping("/join/registercomplete")
    public String registerComplete(HttpServletRequest request,
                                   Model model){

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap!=null) {
            String nickname =(String)flashMap.get("nickname");
            model.addAttribute("nickname",nickname);
        }

        return "register/registercomplete";
    }

    /** 중복되는 아이디 검색하는 매서드.*/
    @PostMapping("/join/searchUserName")
    @ResponseBody
    public String searchUserName(@RequestBody DuplicateUserNameDto duplicateUserNameDto){

        /** 중복되는 아이디를 Mapper로 찾아본다 *
         *  중복되는 아이디가 있다면 null이 아니다.
         *  중복되는 아이디가 없으면 계속진행 가능하므로 true를 반환합니다.
         *  중복되는 아이디가 있으면 계속 진행이 불가능하므로 false를 반환합니다.
         */
        if(joinservice.getUser(duplicateUserNameDto.getUsername()) == null){
            return "true";
        }

        return "false";

    }

    /** 중복되는 이메일 검색하는 매서드.*/
    @PostMapping("/join/searchEmail")
    @ResponseBody
    public String searchEmail(@RequestBody DuplicateUserNameDto duplicateUserNameDto){


        /** 중복되는 email을 Mapper로 찾아본다
         *  중복되는 이메일이 있으면 null이 아니다.
         * */
        if(joinservice.getEmail(duplicateUserNameDto.getEmail()) == null){
            return "true";
        }

        return "false";

    }

    /** 중복되는 mobile 검색하는 매서드.*/
    @PostMapping("/join/searchMobile")
    @ResponseBody
    public String searchMobile(@RequestBody DuplicateUserNameDto duplicateUserNameDto){

        /** 중복되는 mobile을 Mapper로 찾아본다
         *  중복되는 mobile이 있으면 null이 아니다.
         * */
        if(joinservice.getMobile(duplicateUserNameDto.getMobile()) == null){
            return "true";
        }

        return "false";

    }

    /** snsjoinform */
    /** snsjoin은 oauth2로 가입된 회원분들에게 추가적인 정보를 입력하기위함입니다.*/
    @GetMapping("/join/snsjoin")
    public String snsJoin(){
        return "register/snsregister";
    }

    @GetMapping("/join/snsjoinform")
    public String snsJoinForm(@ModelAttribute SnsJoinDto snsJoinDto){
        return "register/snsregisterinput";
    }

    /** oauth2로 신규가입하는 회원들의 추가정보를 저장하는 매서드 */
    @PostMapping("/join/snsjoincomplete")
    public String snsJoinComplete(@Validated @ModelAttribute SnsJoinDto snsJoinDto,BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){


        /** 필드오류  유효성조건은 JoinFormDto에서 확인하세요. */
        if(bindingResult.hasErrors()){
            return "register/snsregisterinput";
        }

        joinservice.userUpdate(snsJoinDto);
        redirectAttributes.addFlashAttribute("nickname",snsJoinDto.getNickName());

        return "redirect:/join/snsregistercomplete";

    }

    @GetMapping("/join/snsregistercomplete")
    public String snsJoinComplete(HttpServletRequest request,
                                  Model model){

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap!=null) {
            String nickname =(String)flashMap.get("nickname");
            model.addAttribute("nickname",nickname);
        }


        return "register/snsregistercomplete";
    }

}
