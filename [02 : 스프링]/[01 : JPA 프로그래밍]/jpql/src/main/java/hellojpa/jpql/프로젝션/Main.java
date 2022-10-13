package hellojpa.jpql.프로젝션;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /**
             * 객체 변환 작업
             */
            List<Object[]> resultList = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();

            List<UserDTO> userDTOs = new ArrayList<>();
            for (Object[] row : resultList) {
                UserDTO userDTO = new UserDTO((String) row[0], (Integer) row[1]);
                userDTOs.add(userDTO);
            }


            /**
             * NEW 명령어 사용
             */
            TypedQuery<UserDTO> query = em.createQuery("SELECT new hellojpa.jpql.프로젝션.UserDTO(m.username, m.age) FROM Member m", UserDTO.class);
            List<UserDTO> resultList2 = query.getResultList();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}

class UserDTO {
    private String username;
    private int age;

    public UserDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}