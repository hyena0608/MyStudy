package hellojpa.jpql.파라미터바인딩;

import hellojpa.jpql.domain.Member;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            String usernameParam = "User1";

            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);

            query.setParameter("username", usernameParam);
            List<Member> resultList1 = query.getResultList();

            List<Member> resultList2 = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
                    .setParameter(1, usernameParam)
                    .getResultList();



            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
