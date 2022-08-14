package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    // 컨트롤러 호출 전에 호출 ( handlerAdapter 호출 전)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        // @RequestMapping : HandlerMethod 사용
        // 정적 리소스 : ResourceHttpRequesetHandeler 사용
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있은ㅁ
        }

        log.info("REQUEST [{}] [{}] [{}]", uuid, requestURI, handler);

        return true; // return false = 더이상 진행 XX , true 는 다음 interceptor or  handlerAdapter 호출(컨트롤러 호출)
    }

    // 컨트롤러 호출이후
    // 컨트롤러 예외 발생 시 호출되지 않음
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    // 컨트롤러 호출 이후
    // 예외 발생 상관없이 항상 호출됨
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);

        // 종료로그 (responce)를 postHandle이 아니라 afterCompletion에서 호출한 이유 => postHandle은 예외가 발생 하면 호출되지 않음
        log.info("RESPONCE [{}] [{}]", logId, requestURI);

        if (ex != null) {
            log.error("afterComplection error!!!", ex);
        }
    }
}
