package hiccup.hiccupstore.user.dto.find;

import lombok.Data;

@Data
public class FindPasswordByEmailDto {

    private String username;
    private String email;

}
