package hellojpa.orphan;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class OrphanApp {

    public static void saveAndRemoveWithCascade(EntityManager em) {

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child1);

        em.persist(parent);

        // 삭제
        Parent findParent = em.find(Parent.class, 1L);
        em.remove(findParent);
    }

    @Entity
    @Data
    public static class Parent {

        @Id
        @GeneratedValue
        private Long id;

        @OneToMany(mappedBy = "parent", orphanRemoval = true)
        private List<Child> children = new ArrayList<>();
    }

    @Entity
    @Data
    public static class Child {

        @Id @GeneratedValue
        private Long id;

        @ManyToOne
        private Parent parent;
    }

}
