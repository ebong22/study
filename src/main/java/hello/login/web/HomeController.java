package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
//    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
//        if (memberId == null) {
//            return "home";
//        }
//
//        //로그인
//        log.info("login? {}", memberId);
////        Member loginMember = memberRepository.findById(memberId);
//        Optional<Member> loginMember = Optional.ofNullable(memberRepository.findById(memberId));
//        if (loginMember.isEmpty()) {
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember.get());
//        return "loginHome";
//    }


//    @GetMapping("/")
    public String homeLogin(
            @CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }


//    @GetMapping("/")
    public String homeLoginV2( HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        //로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3( HttpServletRequest request, Model model) {

        //세션이 없으면 기본 home
        HttpSession session = request.getSession(false); // 세션을 만드려는 의도가 전혀 없기때문에 false (세션을 만드는건 메모리를 사용하는 일이기 때문에)
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home;";
        }

        // 세션에 회원 데이터가 있으면 세션 유지 -> loginHome으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) Member loginMember , Model model) {
        // @SessionAttribute -> spring이 제공해주는 어노테이션
        // 세션에서 name으로 지정한 세션이 있으면 바노한해줌
        // 이 기능은 세션을 생성하지는 않음, 찾아올 뿐

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // 세션에 회원 데이터가 있으면 세션 유지 -> loginHome으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}