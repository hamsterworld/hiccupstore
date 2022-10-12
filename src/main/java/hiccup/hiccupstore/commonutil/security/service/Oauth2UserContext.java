package hiccup.hiccupstore.commonutil.security.service;

import hiccup.hiccupstore.user.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class Oauth2UserContext extends DefaultOAuth2User {

    private UserDto user;
    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public Oauth2UserContext(UserDto user , Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        super(authorities, attributes, nameAttributeKey);
        this.user = user;
    }

    @Override
    public <A> A getAttribute(String name) {
        return super.getAttribute(name);
    }

    public UserDto getAccount() {
        return user;
    }

}
