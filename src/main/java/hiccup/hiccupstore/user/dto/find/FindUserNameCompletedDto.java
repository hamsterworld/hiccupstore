package hiccup.hiccupstore.user.dto.find;

import lombok.Data;

@Data
public class FindUserNameCompletedDto {

    public FindUserNameCompletedDto(String nickname, String username) {
        this.nickname = nickname;
        this.username = username;
    }

    private String nickname;
    private String username;

}
