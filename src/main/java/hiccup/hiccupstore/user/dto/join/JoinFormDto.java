package hiccup.hiccupstore.user.dto.join;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class JoinFormDto {

    @Length(min = 5,max = 10)
    @NotBlank
    private String userName;
    @Length(min = 2,max = 5)
    @NotBlank
    private String nickName;
    @NotBlank
    private String address1;
    @NotBlank
    private String address2;
    @NotBlank
    private String address3;
    @NotBlank
    @Email
    private String email;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    @NotBlank
    @Length(min = 10,max = 15)
    @Pattern(regexp = "([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})")
    private String phone;
    @NotBlank
    @Length(min = 8,max = 16)
    private String password;

    @NotBlank
    @Length(min = 8,max = 16)
    private String passwordConfirm;

    private String userrole;


}
