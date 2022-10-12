package hiccup.hiccupstore.commonutil.security.service;

import hiccup.hiccupstore.user.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User{

    private UserDto userdto;

    public UserDto getUserdto() {
        return userdto;
    }

    public UserContext(UserDto userdto, Collection<? extends GrantedAuthority> authorities) {
        super(userdto.getUserName(), userdto.getPassword(), authorities);
        this.userdto = userdto;
    }

}
