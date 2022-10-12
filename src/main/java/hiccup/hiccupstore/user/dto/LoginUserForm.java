package hiccup.hiccupstore.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserForm {

    @NotBlank
    @Length(min = 5,max = 10)
    private String userName;
    @NotBlank
    @Length(min = 8,max = 16)
    private String password;

}
