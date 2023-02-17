package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    //pointcut signature / 포인트컷 분리하여 정의
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[logAop] {}", joinPoint.getSignature());

        return joinPoint.proceed();
    }
}
