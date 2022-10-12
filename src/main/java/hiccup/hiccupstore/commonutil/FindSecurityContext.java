package hiccup.hiccupstore.commonutil;

import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FindSecurityContext {

    public UserDto getUserDto() {

        try {
            return (UserDto) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal();
        } catch (Exception exception){
            return ((Oauth2UserContext) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal()).getAccount();
        }
    }

}
