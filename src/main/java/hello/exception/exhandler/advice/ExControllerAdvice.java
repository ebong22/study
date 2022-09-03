package hello.exception.exhandler.advice;


import hello.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("hello.exception")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegarExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler // 예외를 생략하면 메서드의 파라미터의 예외로 지정됨 (UserException)
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        log.info("==============");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
        /*
        @ResponseStatus => 어노테이션이기 때문에 HTTP 응답 코드를 동적으로 변경할 수 없음
        위에처럼 ResponseEntity로 반환 => HTTP 응답 코드를 동적으로 변경가능 -> 변수로 선언하고 조건에 따라 HttpStatus 중에 넣어주고 해당 변수를 넘기면 되니까
        */
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부오류");
    }

    // return값을 ModelAndView로 설정하여 화면으로도 반환할 수 있음


}
