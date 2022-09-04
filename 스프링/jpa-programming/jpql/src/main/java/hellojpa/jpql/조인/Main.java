package hellojpa.jpql.조인;

import hellojpa.jpql.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /**
             * 내부 조인
             */
            String teamName = "팀A";
            String query = "SELECT m FROM MEMBER m INNER JOIN m.team t " + "WHERE t.name = :teamName";

            List<Member> members1 = em.createQuery(query, Member.class)
                    .setParameter("teamName", teamName)
                    .getResultList();

            /**
             * 외부 조인
             */
            String query2 = "SELECT m FROM Member m LEFT JOIN m.team t";

            /**
             * 컬렉션 조인
             */

            /**
             * 세타 조인
             */
            String query4 = "SELECT count(m) FROM Member m, Team t WHERE m.username = t.name";

            /**
             * JOIN ON
             */
            String query5 = "SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'";

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
