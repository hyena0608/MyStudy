package hellojpa.jpql.서브쿼리;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            String query1 = "SELECT m FROM Member m WHERE EXISTS " +
                            "(SELECT t FROM m.team t WHERE t.name = '팀A'";

            String query2 = "SELECT o FROM Order o WHERE o.orderAmount > ALL " +
                            "(SELECT p.stockAmount FROM Product p)";

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
