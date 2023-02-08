package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //애플리캐이션 로딩시점에 하나만 만듬
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //설정파일 설정정보에있는 name (persistence.xml)

        // 트랜잭션 단위마다 만듬 (DB커넥션을 얻어서 쿼리를 날리고 종료하는 단위마다 만듬)
        EntityManager em = emf.createEntityManager(); //DB커넥션 하나 얻는것과 비슷

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /* Hello JPA -  어플리케이션 개발*/
        // 이런 트랜젝션 예외처리는 스프링쓰면 해결됨
        /*try{
            //생성
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");

            //조회
            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            //삭제
//            em.remove(findMember);

            //수정
            findMember.setName("HelloJPA"); //수정시 em.pertssist 필요없음. 자바 컬렉션 다루듯 setXXX만 해주면 실제 데이터베이스에도 적용됨
            *//*
            how? : jpa를 통해 엔티티를 가져오면 jpa가 관리함
            jpa가 얘가 변경이 됬는지 안됬는지 트랜잭션을 커밋하는 시점에 체크해서 변경되었으면 update쿼리를 날리고 커밋
            *//*

            List<Member> result = em.createQuery("select m from Member as m", Member.class) //JPQL  객체지향 쿼리언어 ( 엔티티 객체를 대상으로 쿼리 작성 )
//                    .setFirstResult(5) // 5번째부터
//                    .setMaxResults(8)   //8번째 까지 가져와 -->페이징 같은거에 유용
                    .getResultList();

            for (Member member : result ){
                System.out.println("member.name = " + member.getName());
            }

            //생성
//            em.persist(member); //엔티티를 영속성 컨텍스트라는 것에 저장하는 것
            //엔티티메니절르 생성하면 안에 영속성컨테이너가 1:1로 생성됨 --> 자세한건 스프링이랑 연결되서 나중에 설명...

            
            tx.commit();
        }
        catch(Exception e){
            tx.rollback();
        }
        finally {
            em.close();
        }
*/

        /*  영속성 컨텍스트1 - 한 트랜젝션 안에서 해당 */
         try{

             /*//비영속
             Member member = new Member();
             member.setId(101L);
             member.setName("HelloJPA");

             //영속 상태 (1차 캐시에 올라간 상태)
             // 이상태에서 DB에 저장되는게 X. 실제로 실행해보면 before/after사이에 쿼리가 찍히지 X
             System.out.println("===== before =====");
             em.persist(member);
//             em.detach(); //영속성 컨텍스트에서 분리(지움) //cf) em.remove --> 실제 DB에서 삭제
             System.out.println("===== after =====");
             tx.commit(); */

//             Member findMember1 = em.find(Member.class, 101L);
//             Member findMember2 = em.find(Member.class, 101L);
//             System.out.println("findMember1==findMember2 : " + (findMember2 == findMember1));



             /*// 트랜잭션을 지원하는 쓰기지연
             // persist에 쿼리가 나가는게 아니라 차곡차곡 모아뒀다가 트랜잭션 커밋 호출 시 쿼리 나가고 커밋 (관련 기능 jdbc 배치)
             Member member1 = new Member(105L, "A");
             Member member2 = new Member(160L, "B");

             em.persist(member1);
             em.persist(member2);

             System.out.println("==============================="); // 찍힌거 보면 ====== 이후에 쿼리가 날라감
             tx.commit();*/


            /* //수정 변경감지
             Member member = em.find(Member.class, 105L);
             member.setName("zzz");
//             em.persist(member); // 값을 변경해서 persist를 호출해야할 것 같지만 쓰지 않음
             // jpa 목적은 자바 컬렉션 처럼 사용하는 것. setName으로 값만 변경하면 jpa가 변경을 감지하여 알아서 update문 날림
             // 커밋하는 시점에 내부적으로 엔티티와 스냅샷(영속성 컨텍스트로 읽어온 최초시점의 값)을 비교함
             // 이 때 변경 내역이 있다면 update쿼리를 작성해 쓰기지연sql저장소에 넣어둠. 그 후 db에 반영(flush()) -> 커밋
             // flush() 플러시 : 영속성 컨텍스트의 변경내용을 DB에 반영(쿼리 날라감) / 보통 트랜잭션이 커밋될때 발생 / flush자체가 커밋을하는건 아님
             // el.flush() - 직접호출 / 트랜잭션 커밋 - flush 자동호출 / JPQL 쿼리실행 - flush 자동호출
             // tx.commit();
             */


            /* //flush
             Member member = new Member(200L, "member200");
             em.persist(member);

             em.flush(); //flush로 쿼리를 날리기 때문에 ===이전에 쿼리가 찍힘
             // flush를 호출해도 1차캐시 그대로 유지. 단지 변경감지가 일어나고, 변경된건 update쿼리 작성되어 쓰기지연 저장소에 저장되고 쓰기지연 sql저장소에된 쿼리들을 DB에 반영하는 과정
             System.out.println("=========================");

             // 트랜잭션 커밋하는 시점에 DB에 쿼리가 날라감 
             tx.commit();*/
             

             //준영속 상태에 대하여
             //지금 105는 방금 예제에서 사용해서 영속상태
             Member member = em.find(Member.class, 105L);
             member.setName("AAA");

             em.clear(); // 영속성컨텍스트를 초기화 ( 통으로 다 날림 )
             Member member2 = em.find(Member.class, 105L); // clear로 영속성컨텍스트 다 날려서 select 쿼리 두번나가는 것 확인됨

             em.detach(member); //영속성 컨텍스트에서 제외 // 실행해보면 update쿼리 안나감

             System.out.println("--==============================");
             tx.commit();
        }
        catch(Exception e){
            tx.rollback();
        }
        finally {
            em.close();
        }



        emf.close();

    }
}
