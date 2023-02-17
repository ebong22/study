package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV1;
import hello.aop.order.aop.AspectV2;
import hello.aop.order.aop.AspectV3;
import hello.aop.order.aop.AspectV4Pointcut;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class)
//@Import(AspectV2.class)
//@Import(AspectV3.class)
@Import(AspectV4Pointcut.class)
public class AopTest {

    @Autowired
    OrderService orderservice;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService = {}", AopUtils.isAopProxy(orderservice));
        log.info("isAopProxy, orderService = {}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderservice.orderItem("itemA");
    }

    @Test
    void exception() {
        assertThatThrownBy(() -> orderservice.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }

}
