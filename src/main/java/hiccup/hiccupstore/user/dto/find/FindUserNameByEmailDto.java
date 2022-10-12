package hiccup.hiccupstore.user.dto.find;

import lombok.Data;

@Data
public class FindUserNameByEmailDto {

    private String nickname;
    private String email;

}
