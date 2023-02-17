package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    // hello.aop.order 패키지와 하위 패키지 모두
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {
    }

    // 클래스 이럼 패턴 = *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
