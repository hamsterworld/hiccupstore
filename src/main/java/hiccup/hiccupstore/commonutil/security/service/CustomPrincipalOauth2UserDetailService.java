package hiccup.hiccupstore.commonutil.security.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomPrincipalOauth2UserDetailService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("userRequest :  " + userRequest.getClientRegistration());
        System.out.println("AccessToken :  " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttribute :  " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String username = null;
        String password = null;
        String email = null;
        String role = null;
        String nickname = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){

            username = userRequest.getClientRegistration().getClientId()+"_"+oAuth2User.getAttribute("sub"); // google_342342353;
            password = null; //사실크게의미없다.
            email = oAuth2User.getAttribute("email");
            role = "ROLE_USER";
            nickname = oAuth2User.getAttribute("name");

        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            Map response = (Map) oAuth2User.getAttributes().get("response");
            username = userRequest.getClientRegistration().getClientId()+"_"+(String)response.get("id"); // naver_342342353;
            password = null; //사실크게의미없다.
            email = (String) response.get("email");
            nickname = (String) response.get("name");
            role = "ROLE_USER";
        }

        UserDto user = userMapper.getUser(username);

        if( user == null ) {

            UserDto oauth2account = new UserDto(username, email,password, role,nickname);

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(role));

            Oauth2UserContext oauth2AccountContext;

            userMapper.save(oauth2account);
            user = userMapper.getUser(username);
            //가입되어있으면 true
            //가입이 안되있으면 false다.
            user.setSnsflag(false);
            roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(user.getUserrole()));
            oauth2AccountContext = new Oauth2UserContext(user, roles, oAuth2User.getAttributes(), userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName());

            return oauth2AccountContext;

        } else {

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(user.getUserrole()));
            Oauth2UserContext oauth2AccountContext = new Oauth2UserContext(user, roles, oAuth2User.getAttributes(), userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName());

            return oauth2AccountContext;

        }

    }


}
