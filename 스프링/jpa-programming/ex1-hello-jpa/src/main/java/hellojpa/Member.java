package hellojpa;

import hellojpa.embeded.Address;
import hellojpa.embeded.Period;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded Period workPeriod;
    @Embedded Address homeAddress;
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
    }) Address companyAddress;


    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
}
