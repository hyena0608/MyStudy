package hellojpa.collectionvaluetype;

import hellojpa.embeded.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;

    @Embedded Address address;

    // ...
}

class Member {
    // ...

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();
}