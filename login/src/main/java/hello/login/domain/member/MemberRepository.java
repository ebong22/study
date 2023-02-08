package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.*;


@Slf4j
@Repository
public class MemberRepository {

    /*
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 concurrentHashMap, AtomicLong 을 사용 고려
     */
    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static Long sequence = 0L; //static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        log.info("findById : id ={}", id );
        log.info("findById : findMember={}", store.get(id));
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }
    
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
    
    // 테스트용
    public void clearStore() {
        store.clear();
    }
}
