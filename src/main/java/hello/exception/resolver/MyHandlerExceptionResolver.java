package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                /*
                 * ModelAndView를 반환하는 이유
                 * 마치 tryCatch 하듯이 Exception을 처리하여 정상 흐름같이 변경하기 위함
                 * 여기 위에서 IllegalArgumentException이 발생하면 sendError(400)을 호출하여 http상태코드를 500이 아닌 400으로 지정하고
                 * 빈 modelAndView를 반환하여 정상 흐름처럼 처리함
                 *  빈 ModelAndView 반환 : 뷰를 렌더링 하지 않고 정상흐름으로 서블릿이 리턴됨
                 *  ModelAndView 지정하여 반환 : ModelAndView에 뷰나 모델 등의 정보를 지정하여 반환하면 뷰를 렌더링함
                 *  null 반환 : 다음 ExcpeionResolver를 찾아서 실행함. 처리할 수 있는 리졸버가 없으면 예외처리가 안되고 기존에 발생한 예외를 서블릿 밖으로 던져 Was에서 부터 다시 예외가 올라옴
                 */
                return new ModelAndView();

            }
        }
        catch (IOException e){
            log.error("resolver ex", e);
        }

        return null;

    }
}
