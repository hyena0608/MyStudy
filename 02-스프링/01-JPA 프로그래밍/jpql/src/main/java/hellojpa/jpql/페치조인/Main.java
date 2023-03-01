package hellojpa.jpql.페치조인;

import hellojpa.jpql.domain.Member;
import hellojpa.jpql.domain.Team;

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
            String jpql = "select m from Member m join fetch m.team";

            List<Member> members = em.createQuery(jpql, Member.class).getResultList();

            for (Member member : members) {
                System.out.println("username = " + member.getUsername() + ", " + "teamname = " + member.getTeam().getName());
            }

            String jpql2 = "select t from Team t join fetch t.members where t.name = '팀A'";
            List<Team> teams = em.createQuery(jpql2, Team.class).getResultList();

            for (Team team : teams) {

                System.out.println("teamname = " + team.getName() + ", team = " + team);

                for (Member member : team.getMembers()) {
                    System.out.println("->username = " + member.getUsername() + ", member = " + member);
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
