package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.join.JoinFormDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageUserInformationService {

    private final UserMapper usermapper;

    public void MyPageUserInformationUpdate(JoinFormDto joinFormDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }


        UserDto updateduser = UserDto.builder().
                userName(user.getUserName()).
                phone(joinFormDto.getPhone()).
                email(joinFormDto.getEmail()).
                address(joinFormDto.getAddress1() +"/"+ joinFormDto.getAddress2() +"/"+ joinFormDto.getAddress3()).
                build();

        usermapper.updateuserinformation(updateduser.getEmail(),updateduser.getPhone(),updateduser.getAddress(),updateduser.getUserName());

    }

    public void MyPageUserDeleted(String userName){

        usermapper.deleteuser(userName);

    }


}
