package hiccup.hiccupstore.user.controller.mypage;


import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.join.JoinFormDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.service.mypage.MyPageUserInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageUserInformationUpdateController {

    private final PasswordEncoder passwordEncoder;
    private final MyPageUserInformationService myPageUserInformationService;
    private final UserMapper userMapper;
    private final FindSecurityContext findSecurityContext;

    /** 유저정보변경페이지로 가는 매서드입니다. */
    @GetMapping("/mypage/userinformationupadte")
    public String MyPageUserInformationUpdate(Model model){

        UserDto user = findSecurityContext.getUserDto();

        if(user.getUserName().length() >= 9){

            UserDto userInfo = userMapper.getUser(user.getUserName());
            String[] addresssplit = {"","",""};

            if( userInfo.getAddress() != null) {
                addresssplit = userInfo.getAddress().split("/");
            }

            model.addAttribute("userdto",userInfo);
            model.addAttribute("addresssplit",addresssplit);

            return "mypage/userinformationupadteform";

        }

        return "mypage/userinformationupadte";

    }

    /** 유저정보변경페이지에서 비밀번호를 받아 본인이 맞는지 확인하는 매서드입니다.*/
    @PostMapping("/mypage/userinformationupadte")
    public String MyPageUserInformationUpdatePost(String password, Model model){

        UserDto user = findSecurityContext.getUserDto();

        if(passwordEncoder.matches(password,user.getPassword())){

            UserDto userInfo = userMapper.getUser(user.getUserName());
            String[] addresssplit = userInfo.getAddress().split("/");

            model.addAttribute("userdto",userInfo);
            model.addAttribute("addresssplit",addresssplit);

            return "mypage/userinformationupadteform";

        }

        return "redirect:/userinformationupadte";

    }

    /** 변경된 유저정보를 update하는 매서드입니다. */
    @PostMapping("/mypage/updateinformation")
    public String updateUserInformation(JoinFormDto joinFormDto,RedirectAttributes redirectAttributes){

        myPageUserInformationService.MyPageUserInformationUpdate(joinFormDto);
        redirectAttributes.addFlashAttribute("msg","update_ok");

        return "redirect:/mypage";
    }

    /** 회원 탈퇴페이지로 가는 매서드 입니다.*/
    @GetMapping("/mypage/userwithdrawal")
    public String userWithdrawal(Model model){
        UserDto user = findSecurityContext.getUserDto();

        /** SNS회원인지 아닌지 구분하기위해서 쓰는 if문*/
        if(user.getUserName().length() >= 9){
            model.addAttribute("snsUserOrUser",true);
        }
        model.addAttribute("snsUserOrUser",false);
        return "mypage/userwithdrawal";
    }

    /** 회원 탈퇴페이지에서 회원정보탈퇴를 처리하는 매서드입니다.*/
    @PostMapping("/mypage/userwithdrawal")
    public String userWithdrawalPost(HttpServletRequest request,
                                     HttpServletResponse response,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam(defaultValue = "") String password){

        UserDto user = findSecurityContext.getUserDto();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /** Sns유저는 즉각 탈퇴 */
        if(user.getUserName().length() >= 9){
            myPageUserInformationService.MyPageUserDeleted(user.getUserName());
            if(authentication != null){
                new SecurityContextLogoutHandler().logout(request,response,authentication);
            }
            redirectAttributes.addFlashAttribute("msg","DEL_OK");
            return "redirect:/";
        }

        /** 패스워드가 맞다면 회원을 탈퇴시키고 메인페이지로 이동합니다.*/
        if(passwordEncoder.matches(password,user.getPassword())){

            myPageUserInformationService.MyPageUserDeleted(user.getUserName());
            if(authentication != null){
                new SecurityContextLogoutHandler().logout(request,response,authentication);
            }
            redirectAttributes.addFlashAttribute("msg","DEL_OK");
            return "redirect:/";
        }

        return "mypage/userwithdrawal";

    }

}
