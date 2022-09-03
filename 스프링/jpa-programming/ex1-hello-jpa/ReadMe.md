

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