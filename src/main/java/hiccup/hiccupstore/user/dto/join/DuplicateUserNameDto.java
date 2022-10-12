package hiccup.hiccupstore.user.dto.join;

import lombok.Data;

@Data
public class DuplicateUserNameDto {

    private String username;
    private String email;
    private String mobile;
}
