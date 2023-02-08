package hello.web.session;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){

        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse(); //목업 response

        Member member = new Member();
        sessionManager.createSession(member, response);

        // 여기부터 웹브라우저에서 보낸 요청이라 생각하면 됨
        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest(); // 목업 request
        request.setCookies(response.getCookies()); // cookie-> mySessionId= 213243435-2fglj2345 이런 식


        // 클라이언트->서버 요청 시 세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}
