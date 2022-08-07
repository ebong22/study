package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 이면 로그인 실패
     */
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId) // findByLoginId는 optional<Mameber>을 리턴함
                .filter(m -> m.getPassword().equals(password)) //findByLoginId 한 member의 비밀번호도 같으면 그 memeber 리턴
                .orElse(null); // 아니면 null 리텬
    }

}
