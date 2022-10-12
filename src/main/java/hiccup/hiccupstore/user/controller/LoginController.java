package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.LoginUserForm;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final FindSecurityContext findSecurityContext;

    /** login페이지로 가는 매서드입니다. */
    @GetMapping("/login")
    public String login(@ModelAttribute("User") LoginUserForm loginUserForm,
                        @RequestParam(required = false) String error,
                        @RequestParam(required = false) String exception,
                        Model model){

        /** utf-8문제때문에 다시 equals로 확인한후 model에 exception을 한글로 담아줍니다.*/
        if(error != null && exception.equals("Invalid userId")){
            exception = "일치하는 아이디가 없습니다.";
        } else if(error != null && exception.equals("Invalid password")){
            exception = "비밀번호가 일치하지 않습니다.";
        }

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);

        return "login/login";
    }

    /** 접근권환이 없을시 보여주는 페이지 매서드입니다.*/
    @GetMapping("/denied")
    public String denied(@RequestParam(required = false) String exception,Model model){

        UserDto user = findSecurityContext.getUserDto();
        model.addAttribute("username",user.getUserName());
        model.addAttribute("exception",exception);

        return "denied";

    }


}
