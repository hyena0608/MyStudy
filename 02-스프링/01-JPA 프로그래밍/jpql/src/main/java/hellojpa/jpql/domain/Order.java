package hellojpa.jpql.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    private Long id;

    private int orderAmount;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Embedded
    private Address address;
}
