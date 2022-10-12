package hiccup.hiccupstore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer userId;
    private String userName;
    private String nickName;
    private String address;
    private String email;
    private String birth;
    private String phone;
    private String password;
    private String userrole;
    private boolean snsflag = true;

    public UserDto(String userName, String email, String password, String userrole,String nickname) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userrole = userrole;
        this.nickName = nickname;
    }
}
