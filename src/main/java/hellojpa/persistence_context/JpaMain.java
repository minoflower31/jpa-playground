package hellojpa.persistence_context;

import hellojpa.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //sameEntityObject(em);
            sameEntityObjectWithUpdate(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void sameEntityObject(EntityManager em) {
        Member a = em.find(Member.class, 2L);
        Member b = em.find(Member.class, 2L);

        System.out.println(a.equals(b));
        System.out.println(a == b);
    }

    /**
     * 반복가능한 읽기 (Repeatable read) 수준을 애플리케이션 차원에서도 제공
     * @param em
     */
    private static void sameEntityObjectWithUpdate(EntityManager em) {
        Member a = em.find(Member.class, 1L);

        //update 발생
        a.setName("memberBBB");

        Member b = em.find(Member.class, 1L);

        System.out.println(a.equals(b));
        System.out.println(a == b);
    }
}
