package hiccup.hiccupstore.userpick.controller;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.userpick.service.UserPickService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class ApiUserPickController {

    private final UserPickService userPickService;
    private final FindSecurityContext findSecurityContext;

    @PostMapping("/api/userPick")
    public Boolean checkUserPick(@RequestBody HashMap<String, Integer> productMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        Integer productId = productMap.get("productId");
        return  userPickService.existProduct(user.getUserId(), productId);
    }

    @PostMapping("/api/insertUserPick")
    public Boolean insertUserPick(HttpSession session, @RequestBody HashMap<String, Integer> productMap ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        Integer productId = productMap.get("productId");
        if(null != user.getUserId()){
            userPickService.insertUserPick(user.getUserId(), productId);
            return true;
        }
        return false;
    }

    @PostMapping("/api/deleteUserPick")
    public Boolean deleteUserPick(HttpSession session, @RequestBody HashMap<String, Integer> productMap ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        Integer productId = productMap.get("productId");
        if (null != user.getUserId() ) {
            userPickService.deleteUserPick(user.getUserId(), productId);
            return true;
        }
        return false;
    }
}
