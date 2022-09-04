

## 1. 권장하는 식별자 전략

- SEQUENCE, IDENTITY, 랜덤(복합)키를 사용하는게 좋습니다.
- 권장 : Long형 + 대체키 + 키 생성 전략 사용

<br>
<br>
<br>

### 1-1. IDENTITY 생성 전략 특징

- MySQL, PostgreSQL, SQL Server, DB2 에서 사용
- 생성하면 영속성 컨텍스트의 PK값으로 쓰게 된다.
- IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회합니다. 

<br>
<br>
<br>

### 1-2. SEQUENCE 생성 전략 특징

- DB에서 값을 얻어와서 Member에 값을 넣어준 후에 commit한다.

<br>
<br>
<br>

### 1-3. allocationSize (성능 최적화)

- 미리 메모리에 올려놓는다.
- 시퀀스 한 번 호출에 증가하는 수

<br>
<br>
<br>
<br>


## 2. 연관관계의 주인

- 객체의 양방향 관계는 참조가 2군데 있다. (A -> B, B -> A
    - 둘중 테이블의 외래 키를 관리할 곳을 지정해야 한다.
- 연관관계의 주인 : 외래 키를 관리하는 참조
- 주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회
- 주인은 mappedBy 사용 X
- 주인이 아니면 mappedBy 속성으로 주인 지정

<br>
<br>
<br>

### 2-1. 누가 주인 ?

- 외래 키가 있는 곳을 주인으로 정해라

<br>
<br>
<br>

### 2-2. 양방향 연관관계 고려

- 객체 관점에서 양쪽 방향에 모두 값을 입력

```java
public void setTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
}
```

<br>

- 양방향 매핑시 무한 루프 주의

<br>
<br>
<br>

### 2-3. 양방향 매핑 정리

- 단방향 매핑만으로 이미 연관관계 매핑 완료
- 양방향 매핑은 반대 방향으로 조회 기능이 추가된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많다.
- 단방향 매핑을 잘하고 양방향은 필요할 때 추가해도 된다. (테이블에 영향을 주지 않는다.)

<br>
<br>
<br>
<br>

## 3. 일대다 [1:N]

- 일(1)이 연관관계의 주인
- 테이블 일대다 관계는 항상 다(N) 쪽에 외래 키가 있다.
- @JoinColumn을 사용하지 않으면 조인 테이블 방식을 사용한다. (중간 테이블 하나 추가된다.)

<br>
<br>
<br>

### 3-1. 일대다 단방향 단점

- 엔티티가 관리하는 외래 키가 다른 테이블에 있다.
- 연관관계 관리를 위해 추가로 UPDATE SQL 실행

<br>

*일대다 단방향 매핑보다 다대일 양방향 매핑을 사용

<br>
<br>
<br>

### 3-2. 일대다 양방향 정리 []

- 일대다 양방향 매핑은 존재하지 않는다.
- 대신 다대일 양방향 매핑을 사용해야 한다.

<br>
<br>
<br>
<br>

## 4. 일대일 관계

- 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
- 외래 키에 데이터베이스 유니크 제약조건 추가

<br>
<br>
<br>

### 4-1. 주 테이블에 외래 키

- 주 객체가 대상 객체의 참조를 가지는 것 처럼
- 주 테이블에 외래 키를 두고 대상 테이블을 찾는다.
- 객체지향 개발자 선호, JPA 매핑 편리
- 장점
  - 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
- 단점
  - 값이 없으면 외래 키에 null 허용

<br>
<br>
<br>

### 4-2. 대상 테이블에 외래 키

- 대상 테이블에 외래 키가 존재
- 전통적인 데이터베이스 개발자 선호
- 장점
  - 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
- 단점
  - 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩된다.

<br>
<br>
<br>
<br>

## 5. 다대다 관계

- 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다.
- 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야 한다.

```java
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
class Member {
}

@Entity
class Product {
}

@Entity
class MemberProduct {

  @Id @GeneratedValue
  private Long id;

  // @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  // @Id
  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;
}
```

<br>
<br>
<br>
<br>

## 6. 상속 관계 매핑

<br>

### 6-1. 조인 전략

- 엔티티 각각을 모두 테이블로 만든다.
- 기본 키 + 외래 키 전략
- 조회할 때 조인 자주 사용
- 타입을 구분하는 컬럼을 추가해야 한다. (DTYPE)
  - @Inheritance(strategy = InheritanceType.JOINED)
  - @DiscriminatorColumn(name = "DTYPE")
    - 부모 클래스에 구분 컬럼을 지정한다.
  - @DiscriminatorValue("M")
    - 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다.
- 장점
  - 테이블 정규화
  - 외래 키 참조 무결성 제약조건 활용
  - 저장공간 효율적
- 단점
  - 조인 사용으로 인한 성능 저하
  - 조회 쿼리 복잡
  - 데이터를 등록할 INSERT SQL을 두 번 실행한다.

<br>

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
}

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

  private String director;
  private String actor;
}
```

<br>
<br>
<br>

### 6-2. 단일 테이블 전략

- 테이블을 하나만 사용한다.
- 구분 컬럼 (DTYPE)으로 어떤 자식 데이터가 저장되었는지 구분한다.
- 장점
  - 조회할 떄 조인을 사용하지 않으므로 일반적으로 가장 빠르다.
- 단점
  - 자식 엔티티가 매핑한 컬럼은 모두 null을 허용하는 점을 주의해야 한다.
  - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다.
  - 상황에 따라서는 조회 성능이 오히려 느릴 수 있다.
- 특징
  - 구분 컬럼을 꼭 사용해야 한다.
    - @DiscriminateColumn

<br>

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
}

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

  private String director;
  private String actor;
}
```

<br>
<br>

```sql
Hibernate: 
    
    create table Item (
       DTYPE varchar(31) not null,
        ITEM_ID bigint not null,
        name varchar(255),
        price integer not null,
        artists varchar(255),
        author varchar(255),
        isbn varchar(255),
        actor varchar(255),
        director varchar(255),
        primary key (ITEM_ID)
    )
```

<br>
<br>
<br>


### 6-3. 구현 클래스마다 테이블 전략

- 자식 엔티티마다 테이블을 만든다.
- 자식 테이블 각각에 필요한 컬럼이 모두 있다.
- 장점
  - 서브 타입을 구분해서 처리할 때 효과적이다.
  - not null 제약 조건을 사용할 수 있다.
- 단점
  - 여러 자식 테이블을 함께 조회할 때 성능이 느리다. (UNION)
  - 자식 테이블을 통합해서 쿼리하기 어렵다.
- 특징
  - 구분 컬럼을 사용하지 안흔ㄴ다.
  - 추천하지 않는 전략이다.

<br>

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {

  @Id @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;

  private String name;
  private int price;
}

@Entity
public class Movie extends Item {

  private String director;
  private String actor;
}
```

<br>
<br>
<br>
<br>

## 7. 매핑 정보 상속 (@MappedSuperclass)

- 테이블과 매핑하지 않고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할
- 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
- @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능

<br>

```java
import javax.persistence.Entity;

@MappedSuperclass
public abstract class BaseEntity {

  private String createdBy;
  private LocalDateTime createdDate;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;
}

@Entity
public class Member extends BaseEntity {}
```

<br>
<br>
<br>
<br>

## 8. 프록시

- 프록시 객체는 처음 사용할 때 한 번만 초기화
- 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다.
- 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
- 프록시 객체는 원본 엔티티를 상속 받는다. 따라서 타입 체크 시 instance of 사용한다.
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 인티티를 반환한다.
- 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제가 발생한다.

<br>
<br>
<br>
<br>

## 9. 프록시와 즉시로딩

- 가급적 지연 로딩만 사용
- 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
- 즉시 로딩은 JPQL에서 N + 1 문제를 일으킨다.
- @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY
- @OneToMany, @ManyToMany는 기본이 지연 로딩
<br>
<br>
<br>

### 9-1. 즉시 로딩, 지연 로딩 정리

- 지연 로딩 (LAZY)
  - 연관된 엔티티를 프록시로 조회한다.
  - 프록시를 실제 사용할 때 초기화하면서 데이터베이스를 조회한다.
- 즉시 로딩 (EAGER)
  - 연관된 엔티티를 즉시 조회한다.
  - 하이버네이트는 가능하면 SQL 조인을 사용해서 한 번에 조회한다.

<br>
<br>
<br>

### 9-2. 프록시와 컬랙션 래퍼

- org.hibernate.collection.internal.PersistentBag
  - 하이버네이트는 엔티티를 영속 상태로 만들 때 엔티티에 컬렉션이 있으면 컬렉션을 추적하고 
  - 관리할 목적으로 원본 컬렉션을 하이버네이트가 제공하는 내장 컬렉션으로 변경하는데 이것을 컬렉션 래퍼라고 한다.
- 엔티티를 지연 로딩하면 프록시 객체를 사용해서 지연 로딩을 수행하지만 주문 내역 같은 컬렉션은 컬렉션 래퍼가 지연 로딩을 처리해준다.
- 컬렉션 래퍼도 컬렉션에 대한 프록시 역할을 한다.

<br>
<br>
<br>

### 9-3. 컬렉션에 FetchType.EAGER 사용 시 주의점

- 컬렉션을 하나 이상 즉시 로딩하는 것은 비권장
  - 컬렉션과 조인한다 -> [1:N] 조인
  - 일대다 조인 -> 결과 데이터가 [N] 쪽에 있는 수 만큼 증가
  - 서로 다른 컬렉션을 2개 이상 조인한다면 [N * M] 만큼 SQL 실행
    - 애플리케이션 성능 저하 원인
- 컬렉션 즉시 로딩은 항상 외부 조인을 사용한다.
  - [N:1] 관계인 테이블에서 외래 키에 not null 제약 조건을 걸어두면 항상 내부 조인을 사용해도 된다.
  - 하지만 외래 키를 한번도 사용하지 않은 값이 있을 때 내부 조인을 하면 조회되지 않는 문제가 발생한다.
  - JPA는 [1:N]관계를 즉시 로딩할 때 항상 외부 조인을 사용한다.

<br>

- @ManyToOne, @OneToOne (default : EAGER)
  - (optional = false) : 내부 조인
  - (optional = true) : 외부 조인
- @OneToMany, @ManyToMany (default : LAZY)
  - (optional = false) : 외부 조인 
  - (optional = true) : 외부 조인

<br>
<br>
<br>
<br>

## 10. 영속성 전이 : CASCADE

- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때

<br>
<br>
<br>

### 10-1. 영속성 전이 : 저장

<br>

```java
@Entity
public static class Parent {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<Child> children = new ArrayList<>();
}

@Entity
public static class Child {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  private Parent parent;
}
```

<br>

- cascade = CascadeType.PERSIST
  - 부모를 영속화할 때 연관된 자식들도 함께 영속화한다.
- 영속성 전이는 연관관계를 매핑하는 것과는 아무 관련이 없다.


<br>
<br>
<br>

### 10-2. 영속성 전이 : 삭제

<br>



```java
@Entity
public static class Parent {

  @Id
  @GeneratedValue
  private Long id;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
  private List<Child> children = new ArrayList<>();
}

@Entity
public static class Child {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  private Parent parent;
}
  
void remove() {
        Parent findParent=em.find(Parent.class,1L);
        em.remove(findParent);
}
```

<br>

- cascade = CascadeType.REMOVE
  - 부모 엔티티만 삭제하면 연관된 자식 엔티티도 함께 삭제된다.

<br>
<br>
<br>

### 10-3. CASCADE의 종류

```java
public enum CascadeType {
    
    /** Cascade all operations */
    ALL, 

    /** Cascade persist operation */
    PERSIST, 

    /** Cascade merge operation */
    MERGE, 

    /** Cascade remove operation */
    REMOVE,

    /** Cascade refresh operation */
    REFRESH,

    /**
     * Cascade detach operation
     *
     * @since 2.0
     * 
     */   
    DETACH
}
```

<br>
<br>
<br>
<br>

## 11.  고아 객체 (ORPHAN)

- JPA는 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제하는 기능을 제공한다. : 고아 객체 제거
- 부모 엔티티의 컬렉션에서 자식 엔티티의 참조만 제거하면 자식 엔티티가 자동으로 삭제된다.

<br>

```java
@Entity
public static class Parent {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Child> children = new ArrayList<>();
}
```

<br>

- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능이다.
- 이 기능은 참조하는 곳이 하나일 때만 사용해야 한다.
- 만약 삭제한 엔티티를 다른 곳에서도 참조한다면 문제가 발생한다.
- orphanRemoval은 @OneToOne, @OneToMany에서만 사용한다.

<br>
<br>
<br>

### 11-1. 영속성 전이 + 고아 객체, 생명주기

- CascadeType.ALL + orphanRemoval = true를 동시에 사용
  - 두 옵션을 모두 활성화하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있다.

<br>

- 자식을 저장하려면 부모에 등록만 하면 된다. (CASCADE)

```java
Parent parent = em.find(Parent.class, parentId);
parent.addChild(child1);
```

<br>

- 자식을 삭제하려면 부모에서 제거하면 된다 (orphanRemoval)

```java
Parent parent = em.find(Parent.class, parentId);
parent.getChildren().remove(removeObject);
```

<br>
<br>
<br>
<br>

## 12. 값 타입

- JPA의 데이터 타입은 크게 엔티티 타입, 값 타입이 있다.

<br>

### 12-1. 기본 값타입

- 자바 기본 타입 (int, double, ...)
- 래퍼 클래스 (Integer, ...)
- String

<br>
<br>
<br>

### 12-2. 임베디드 타입 (복합 값 타입) 

- 새로운 값 타입을 직접 정의해서 사용 : 임베디드 타입

<br>

```java

```