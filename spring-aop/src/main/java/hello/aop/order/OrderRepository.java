package hello.aop.order;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepository {

    public String save(String itemId) {
        log.info("[OrderRepository] 실행");

        // 저장로직
        if(itemId.equals("ex")){
            throw new IllegalStateException("[OrderRepository] 에외 발생");
        }

        return "OK";
    }
}
