package hiccup.hiccupstore.commonutil.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid Username or Password";

        if(exception instanceof UsernameNotFoundException){
            errorMessage = "Invalid userId";
            setDefaultFailureUrl("/login?error=true&exception="+exception.getMessage());
        } else if(exception instanceof BadCredentialsException){
            errorMessage = "Invalid Password";
            setDefaultFailureUrl("/login?error=true&exception="+exception.getMessage());
        } else if(exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid secretKey";
            setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage());
        }
//        }else if(exception instanceof NewSnsUserException){
//            request.setAttribute("userdto",((NewSnsUserException) exception).getUser());
//            setDefaultFailureUrl("/snsjoin");
//        }

        /** sns유저 join logic */
        super.onAuthenticationFailure(request,response,exception);

    }
}
