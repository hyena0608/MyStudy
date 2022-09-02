package hellojpa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public Member() {}
}
