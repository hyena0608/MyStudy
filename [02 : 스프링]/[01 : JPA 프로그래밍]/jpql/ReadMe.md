
## 1. TypeQuery, Query

- 작성한 JPQL을 실행하려면 쿼리 객체를 만들어야 한다.
- TypeQuery
  - 반환할 타입을 명확하게 지정할 수 있을 때
- Query
  - 반환 타입을 명확하게 지정할 수 없을 때

<br>
<br>
<br>
<br>

## 2. 프로젝션

- SELECT 절에 조회할 대상을 지정하는 것

<br>
<br>
<br>
<br>

## 3. 페이징 API

- 데이터베이스마다 페이징 처리하는 SQL 문법이 다르다.
- JPA는 페이징을 두 API로 추상화했다.
- setFirstResult(int startPosition)
  - 조회 시작 위치
- setMaxResults(int maxResult)
  - 조회할 데이터 수

<br>

페이징 SQL을 더 최적화하고 싶다면 JPA가 제공하는 페이징 API가 아닌 네이티브 SQL을 직접 사용해야 한다.

<br>
<br>
<br>
<br>

## 4. JPQL 조인

- 내부 조인
  - JPQL 조인의 가장 큰 특징은 연관 필드를 사용한다는 것이다.
- 외부 조인
- 컬렉션 조인
  - 일대다 관계나 다대다 관계처럼 컬렉션을 사용하는 곳에 조인하는 것을 컬렉션 조인이라 한다.
- 세타 조인
- JOIN ON절

<br>
<br>
<br>
<br>

## 5. 서브 쿼리

- 서브쿼리를 WHERE, HAVING 절에서만 사용할 수 있고 SELECT, FROM 절에서는 사용할 수 없다.

<br>
<br>
<br>
<br>

## 6. 경로 표현식

- 상태 필드
- 연관 필드
  - 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티
  - 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬레션

<br>

```java
@Entity
public class Member {
    
    @Id @GeneratedValue
    private Long id;
    
    @Column(name = "name")
    private String username;
    private int age;
    
    @ManyToOne(..)
    private Team team;
    
    @OneToMany(..)
    private List<Order> orders;
}
```

<br>

- 상태 필드 : t.username, t.age
- 단일 값 연관 필드 : m.team;
- 컬렉션 값 연관 필드 : m.orders

<br>
<br>
<br>

### 6-1. 경로 표현식과 특징

- 상태 필드 경로
  - 경로 탐색의 끝
- 단일 값 연관 경로
  - 묵시적으로 내부 조인이 일어난다.
  - 단일 값 연관 경로는 계속 탐색할 수 있다.
- 컬렉션 값 연관 경로
  - 묵시적으로 내부 조인이 일어난다.
  - 더는 탐색할 수 없다.
  - 단 FROM 절에서 조인을 통해 별칭을 얻으면 별칭으로 탐색할 수 있다.

<br>
<br>
<br>

### 6-2. 컬렉션 값 연관 경로 탐색

- 컬렉션에서 경로 탐색을 하고 싶으면 조인을 사용해서 새로운 별칭을 획득해야 한다.

```java
select m.username from Team t join t.member m
```

<br>
<br>
<br>
<br>

## 7. 페치 조인

- 페치 조인은 JPQL에서 성능 최적화를 위해 제공하는 기능이다.
- 연관된 엔티티나 컬렉션을 한 번에 같이 조회하는 기능이다.
- join fetch 명령어로 사용할 수 있다.

<br>
<br>
<br>

### 7-1. 엔티티 페치 조인

- 회원 엔티티 조회하면서 연관된 팀 엔티티도 함께 조회

```java
select m from Member m join fetch m.team
```

<br>

이렇게 하면 연관된 엔티티나 컬렉션을 함께 조회하는데 여기서는 회원(m)과 팀(m.team)을 함께 조회한다.

<br>

- 페치 조인 사용
  - 페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩 발생 안함

```java
String jpql = "select m from Member m join fetch m.team";

List<Member> members = em.createQuery(jpql, Member.class).getResultList();

for (Member member : members) {
    System.out.println("username = " + member.getUsername() + ", " + "teamname = " + member.getTeam().name());
}
```

<br>
<br>
<br>

### 7-2. 컬렉션 페치 조인

- 일대다 관계인 컬렉션을 페치 조인하면 결과가 증가할 수 있다.

```java
String jpql = "select t from Team t join fetch t.members where t.name = '팀A'";
List<Team> teams = em.createQuery(jpql, Team.class).getResultList();

for (Team team : teams) {

    System.out.println("teamname = " + team.getName() + ", team = " + team);
    
    for (Member member : team.getMembers()) {
        System.out.println("->username = " + member.getUsername() + ", member = " + member);    
    }
}
```

<br>
<br>
<br>

### 7-3. 페치 조인과 DISTINCT

- JPQL의 DISTINCT 명령어는 SQL에 DISTINCT를 추가흐는 것은 물론이고 애플리케이션에서 한 번 더 중복을 제거한다.

<br>
<br>
<br>

### 7-4. 페치 조인의 특징과 한계

- 페치 조인 대상에는 별칭을 줄 수 없다.
- 둘 이상의 컬렉션을 페치할 수 없다.
  - 컬렉션 * 컬렉션은 카테시안 곱이 만들어지므로 주의
- 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
- hibernate에서 컬렉션을 페치 조인하고 페이징 API를 사용하면 경고 로그를 남기면서 메모리에서 페이징 처리를 한다.
  - 성능 이슈와 메모리 초과 예외 발생할 수 있다.
- 억지로 페치 조인을 사용하기 보다 여러 테이블에서 필요한 필드들만 조회해서 DTO로 반환하는 것이 더 효과적일 수 있다.

<br>
<br>
<br>
<br>

## 8. 다형성 쿼리

- JPQL로 부모 엔티티를 조회하면 그 자식 엔티티도 함께 조회한다.
- TYPE, TREAT

<br>

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {..}

@Entity
@DiscriminatorValue("B")
public class Book extends Item {
    // ...
    private String author;
}
```

<br>
<br>
<br>

### 8-1. TYPE

- 엔티티의 상속 구조에서 조회 대상을 특정 자식 타입으로 한정할 때 사용
- Item 중에 Book, Movie를 조회

```java
select i from Item i
where type(i) IN (Book, Movie)
```

<br>
<br>
<br>

### 8-2. TREAT

- 자바의 타입 캐스팅과 비슷하다.
- 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용

<br>

JPQL을 보면 treat를 사용해서 부모 타입인 Item을 자식 타입인 Book으로 다룬다.
따라서 author 필드에 접근할 수 있다.

```java
select i from Item i where treat(i as Book).author = 'kim'
```

<br>
<br>
<br>
<br>

## 9. 엔티티 직접 사용

<br>

### 9-1. 기본 키 값

- 객체 인스턴스는 참조 값으로 식별하고 테이블 로우는 기본 키 값으로 식별한다.

```java
String qlString = "select m from Member m where m = :member";
List resultList = em.createQuery(qlString)
        .setParameter("member", member)
        .getResultList();
```

<br>
<br>
<br>

### 9-2. 외래 키 값

```java
Team team = em.find(Team.class, 1L);

String qlString = "select m from Member m where m.team = :team";
List resultList = em.createQuery(qlString)
        .setParameter("team", team)
        .getResultList();
```

<br>
<br>
<br>
<br>

## 10. Named 쿼리

- 동적 쿼리
  - em.createQuery("select ...")처럼 JPQL을 문자로 완성해서 직접 넘기는 것을 동적 쿼리라고 한다.
  - 런타임 특정 조건에 따라 JPQL을 동적으로 구성할 수 있다.
- 정적 쿼리
  - 미리 정의한 쿼리에 이름을 부여해서 필요할 때 사용할 수 있다.
  - 이것을 Named 쿼리라고 한다.
  - Named 쿼리는 한 번 정의하면 변경할 수 없는 정적인 쿼리다.
- @NameQuery 어노테이션을 사용한다.

<br>
<br>
<br>

### 10-1. Named 쿼리를 어노테이션에 정리

```java
@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member {}

public static void main(String[] args) {
    //...
  
    List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
            .setParamemter("username", "회원1")
            .getResultList();
}
```

<br>
<br>
<br>

### 10-2. 하나의 엔티티에 2개 이상의 Named 쿼리

- @NamedQueries 사용
- 
<br>

```java
@Entity
@NamedQuery({
        @NamedQuery(
                name = "Member.findByUsername",
                query = "select m from Member m where m.username = :username"),
        @NamedQuery(
                name = "Member.count",
                query = "select count(m) from Member m"
})
public class Member {}
```