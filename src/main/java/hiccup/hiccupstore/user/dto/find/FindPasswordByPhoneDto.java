package hiccup.hiccupstore.user.dto.find;

import lombok.Data;

@Data
public class FindPasswordByPhoneDto {

    private String username;
    private String phone;

}
