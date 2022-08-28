package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {
    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION      = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE        = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI    = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME   = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE    = "javax.servlet.error.status_code";

    // Get, Post 한번에 처리해주려고 RequestMapping
    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        // request.attribute 서버가 담아준 오류정보
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage800(HttpServletRequest reaquest, HttpServletResponse response) {
        log.info("errorPage 500");
        // request.attribute 서버가 담아준 오류정보
        printErrorInfo(reaquest);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        // was는 오류페이지를 단순히 다시 요청하는 것 뿐만 아니라 오류정보를 request의 attribute 추가해서 넘겨줌
        // 필요하면 오류페이지에서 전달된 오류정보를 사용할 수 있음
        //예외
        log.info("ERROR_EXCEPTION: ex={}", request.getAttribute(ERROR_EXCEPTION));
        //예외 타입
        log.info("ERROR_EXCEPTION_TYPE: {}}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        //오류 메세지
        log.info("ERROR_MESSAGE: {}}", request.getAttribute(ERROR_MESSAGE));
        //클라이언트 요청 URI
        log.info("ERROR_REQUEST_URI: {}}", request.getAttribute(ERROR_REQUEST_URI));
        // 오류가 발생한 서블릿 이름
        log.info("ERROR_SERVLET_NAME: {}}", request.getAttribute(ERROR_SERVLET_NAME));
        // HTTP 상태코드
        log.info("ERROR_STATUS_CODE: {}}", request.getAttribute(ERROR_STATUS_CODE));
        // 서블릿이 제공하는 dispatchType
        // 해당 요청이 클라이언트로부터 발생한 정상 요청인지, 아니면 오류페이지를 찾기위한 서버 내부요청인지 구분할 수 있는 값
        log.info("dispatchType= {}}", request.getDispatcherType());
    }
}
