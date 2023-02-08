package example.restApi.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UpdateUserForm {

    @NotBlank
    private String passWord;

    @NotNull
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String phoneNumber;

    @NotNull
    @Length( max = 50 )
    private String address;

    public UpdateUserForm(){
    }

    public UpdateUserForm(String loginId, String passWord, String phoneNumber, String address) {
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
