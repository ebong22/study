package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* hello.aop.order..*(..))") // hello.aop.order 패키지와 하위 패키지 모두
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[logAop] {}", joinPoint.getSignature());

        return joinPoint.proceed(); // joinPoint.proceed()를 호출하지 않으면 실제 타겟로직이 호출되지 않음
    }
}
