package hiccup.hiccupstore.user.dto.find;

import lombok.Data;

@Data
public class FindPasswordCompletedDto {

    public FindPasswordCompletedDto(String password, String username) {
        this.password = password;
        this.username = username;
    }

    private String password;
    private String username;

}
