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
            /* Create
            Member member = new Member();
            member.setId(2L);
            member.setName("2Name");

            em.persist(member);
            */

            /* Update
            //JPA 를 통해서 객체를 가져오면 persist 할 필요가 없다.
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("Name1");
            */

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();

            for(Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
