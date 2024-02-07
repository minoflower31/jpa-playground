package hellojpa.flush;

import hellojpa.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

//            for (Member member : members) {
//                System.out.println("member.getId() = " + member.getId());
//                System.out.println("member.getName() = " + member.getName());
//            }

            Member member = members.get(0);

            em.detach(member);
            member.setName("member55555");

            List<Member> afterMembers = em.createQuery("select m from Member m", Member.class).getResultList();
            Member member2 = afterMembers.get(0);

            System.out.println("change member.name = " + (member.getName()));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void flushNotFindMethod(EntityManager em) {
        Member member = new Member(20L, "member20");
        em.persist(member);

        Member findMember = em.find(Member.class, 20L);

        System.out.println("============");

        System.out.println(findMember.equals(member));
    }


}
