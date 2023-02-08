package example.restApi.api;

import example.restApi.domain.UpdateUserForm;
import example.restApi.domain.User;
import example.restApi.domain.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"User 컨트롤러"})
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;


    @ApiOperation(value = "회원 생성(회원가입)")
    @PostMapping("/insert")
    public User save( User user ){
        return userRepository.save(user);
    }

    @ApiOperation(value = "회원정보 수정")
    @PostMapping("/update/{id}")
    public void update( @PathVariable Long id, UpdateUserForm updateUser){
        userRepository.Update(id, updateUser);
    }

    @ApiOperation(value = "id로 회원 찾기")
    @PostMapping("/search/{id}")
    public User findById (@PathVariable Long id){
        return userRepository.findById(id);
    }

    @ApiOperation(value = "전체 회원 리스트")
    @GetMapping("/list")
    public List<User> findAll(){
        return userRepository.findAll();
    }
    
    //swagger 주소
    //http://localhost:8080/swagger-ui/
}
