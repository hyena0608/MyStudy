package hellojpa;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;
}
