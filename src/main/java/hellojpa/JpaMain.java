package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //애플리케이션에 하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //트랜잭션 필요 시 생성 및 close, 쓰레드 간 공유 X
        EntityManager em = emf.createEntityManager();

        //JPA 의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.changeTeam(team);
            em.persist(member);

            /*
            * team 에 member 를 add 하지 않고,
            * 밑에 flush 와 clear 를 주석처리하면
            * members 의 출력이 아무것도 되지 않는다.
            * add: O, flush/clear: O -> 출력 O
            * add: O, flush/clear: X -> 출력 O
            * add: X, flush/clear: O -> 출력 O
            * add: X, flush/clear: X -> 출력 X
            */
            //team.getMembers().add(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            System.out.println("==========");
            for (Member m : members) {
                System.out.println("m = " + m.getName());
            }
            System.out.println("==========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
