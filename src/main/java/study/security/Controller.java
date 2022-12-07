package study.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {
    @RequestMapping("/home")
    public String home (){
        return "home";
    }

}
