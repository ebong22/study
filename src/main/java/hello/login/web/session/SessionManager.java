package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>(); // 동시성 문제가 있을 땐 hashMap이 아니라 이걸로 해야함

    /**
     * 세션 생성
     * sessionId 생성 (임의의 추정 불가능한 랜덤 값)
     * 세션 저장소에 sessionId와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     * @param value
     * @param response
     */
    public void createSession(Object value, HttpServletResponse response) {

        //세션  id 를 생성하고 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString(); // uuid -> universal unique ID???? 무튼 전우주에서 유일한 아이디인 것 처럼 임의의 값 제공하는 자바 기본함수
        sessionStore.put(sessionId, value);

        //쿠키 생성
        // 컨트롤 알트 C -> 상수지정하는 단축키(상단에 static final로 정의 해줌)
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);

    }

    /**
     * 세션 조회
     * @param request
     * @return
     */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookies(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 전송된 쿠키에서 cookieName 조회
     * @param request
     * @param cookieName
     * @return
     */
    private Cookie findCookies(HttpServletRequest request, String cookieName) {

        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    /**
     * 세션 만료
     * @param request
     */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookies(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }



}
