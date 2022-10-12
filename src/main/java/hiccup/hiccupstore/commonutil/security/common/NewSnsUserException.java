package hiccup.hiccupstore.commonutil.security.common;

import hiccup.hiccupstore.user.dto.UserDto;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class NewSnsUserException extends OAuth2AuthenticationException {

    private UserDto user;

    public NewSnsUserException(String message) {
        super(message);
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
