package example.restApi.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class User {

    @ApiModelProperty(value = "시퀀스")
    private Long id;

    @ApiModelProperty(value = "사용자 ID")
    @NotBlank
    private String loginId;

    @ApiModelProperty(value = "비밀번호")
    @NotBlank
    private String passWord;

    @ApiModelProperty(value = "핸드폰 번호")
    @NotNull
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String phoneNumber;

    @ApiModelProperty(value = "주소")
    @NotNull
    @Length( max = 50 )
    private String address;

    public User(){
    }

    public User(String loginId, String passWord, String phoneNumber, String address) {
        this.loginId = loginId;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
