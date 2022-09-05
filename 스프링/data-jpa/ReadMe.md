

## 1. 공통 인터페이스

- JpaRepository 인터페이스 : 공통 CRUD 제공
- 제니릭은 <엔티티 타입, 식별자 타입> 설정

```java
public interface JpaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}

public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

<br>
<br>
<br>

### 1-1. JPA 공통, 공통 X

- 스프링 데이터
  - interface Repository
  - interface CrudRepository extends Repository
  - interface PagingAndSortingRepository extends CrudRepository

<br>
<br>

- 스프링 데이터 JPA
  - interface JpaRepository extends PagingAndSortingRepository

<br>

- 제네릭 타입
  - T : 엔티티
  - ID : 엔티티의 식별자 타입
  - S : 엔티티와 그 자식 타입

<br>
<br>

- 주요 메서드
  - save(S) : 새로운 엔티티 저장, 이미 있는 엔티티 병합
  - delete(T) : EntityManager.remove() 호출
  - findById(ID) : EntityManager.find() 호출
  - getOne(ID) : 엔티티를 프록시로 조회한다. EntityManager.getReference() 호출
  - findAll(...) : 정렬이나 페이징 조건을 파라미터로 제공할 수 있다.

<br>

JpaRepository는 대부분의 공통 메서드를 제공한다.

<br>
<br>
<br>
<br>

## 2. 쿼리 메서드 (메서드 이름으로 쿼리 생성) [* 자주 쓰임]
[쿼리 메서드 문서 링크](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation)
- 쿼리 메서드 이름 가지고 가져올 수 있다.
  - 길어지면 사용하기 어렵다.
- 짧은 쿼리에 사용
- 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 꼭 함께 변경해야 한다. 
  - 그렇지 않으면 애플리케이션을 시작하는 시점에 오류 발생 
  - 로딩 시점에 오류를 인지할 수 있는 것이 스프링 데이터 JPA 장점

<br>
<br>
<br>
<br>

## 3. JPA NamedQuery

- 스프링 데이터 JPA를 사용하면 실무에서 Named Query를 직접 등록해서 사용하는 일은 드물다.
  - 애플리케이션 로딩 시점에 오류를 잡을 수 있는 장점이 있다.
- 대신 @Query 를 사용해서 리파지토리 메소드에 쿼리를 직접 정의 하자

<br>

```java
@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member {..}

public interface MemberRepository extends JpaRepository<Member, Long> {
  //    @Query(name = "Member.findByUsername")
  List<Member> findByUsername(@Param("username") String username);
}
```

<br>
<br>
<br>
<br>

## 4. @Query, 리포지터리 메서드에 쿼리 정의 [* 자주 쓰임]

- 메서드에 JPQL 쿼리 작성
- 실행할 메서드에 정적 쿼리를 직접 작성해서 이름 없는 Named 쿼리로 볼 수 있다.
- JPA NamedQuery처럼 애플리케이션 실행 시점에 문법 오류를 발견할 수 있다.
- 파싱해서 SQL로 다 만들어 놓는데 문법 오류가 있으면 잡아준다. (위와 동일)

```java
public interface MemberRepository extends JpaRepository<Member, Long> {
  @Query("select m from Member m where m.username = :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
```

<br>
<br>
<br>
<br>

## 5. @Query, 값, DTO 조회

```java
public interface MemberRepository extends JpaRepository<Member, Long> {
  @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();
}

public class MemberDto {

  private Long id;
  private String username;
  private String teamName;

  public MemberDto(Long id, String username, String teamName) {
    this.id = id;
    this.username = username;
    this.teamName = teamName;
  }
}
```

<br>

- DTO로 직접 조회하려면 JPA의 new 명령어를 사용해야 한다.
- 때문에 생성자가 맞는 DTO가 필요하다.


<br>
<br>
<br>
<br>

## 6. 파라미터 바인딩

<br>
<br>

### 6-1. 위치, 이름 기반

- 위치 기반
- 이름 기반 [* 주로 사용]

```sql
select m from Member m where m.username = ?0 // 위치 기반
select m from Member m where m.username = :name // 이름 기반
```

<br>
<br>
<br>

### 6-2. 파라미터 바인딩

```java
@Query("select m from Member m where m.username = :username and m.age = :age")
List<Member> findUser(@Param("username") String username, @Param("age") int age);
```

<br>
<br>
<br>

### 6-3. 컬렉션 파라미터 바인딩

```java
@Query("select m from Member m where m.username in :names")
List<Member> findByNames(@Param("names") Collection<String> names);
```

<br>
<br>
<br>
<br>

## 7. 반환 타입 (컬렉션, 단건, Optional)

- 컬렉션
  - 결과 없음 : 빈 컬렉션 반환
- 단건
  - 결과 없음 : null 반환
  - 결과 복수 : NonUniqueResultException
- 단건 (Optional)

```java
List<Member> findListByUsername(String username); // 컬렉션
Member findMemberByUsername(String username); // 단건
Optional<Member> findOptionalMemberByUsername(String username); // 단건 Optional
```

<br>
<br>
<br>
<br>

## 8. JPA 페이징과 정렬

<br>
<br>

### 8-1. 순수 JPA 페이징과 정렬

```java
public List<Member> findByPage(int age, int offset, int limit) {
    return em.createQuery("select m from Member m where m.age = :age order by m.username desc", Member.class)
            .setParameter("age", age)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
}
```

<br>
<br>
<br>

### 8-2. 스프링 데이터 JPA 페이징과 정렬

- 페이징과 정렬 파라미터
  - 정렬 기능
    - org.springframework.data.domain.Sort
  - 페이징 기능
    - org.springframework.data.domain.Pageable

<br>
<br>

- 특별한 반환 타입
  - 추가 count 쿼리 결과를 포함하는 페이징
    - org.springframework.data.domain.Page
      - Page Index는 0부터 시작
  - 추가 count 쿼리 없이 다음 페이지만 확인 가능 (내부적으로 limit + 1 조회)
    - org.springframework.data.domain.Slice

<br>

```java
Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 
Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
List<Member> findByUsername(String name, Sort sort);
```

<br>
<br>
<br>

### 8-3. count Query -> 조인할 필요 없다. (성능 개선)

- countQuery를 분리해서 세라.
- countQuery = "select count(m) from Member m"

```java
@Query(value = "select m from Member m left join m.team t",
countQuery = "select count(m) from Member m")
Page<Member> findByAge(int age, Pageable pageable);
```

<br>
<br>
<br>

### 8-4. Page 유지하며 Entity -> DTO 변환하는 법

- Entity를 노출시키지 말자

```java
Page<Member> page = memberRepository.findByAge(age, pageRequest);

Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
```

<br>
<br>
<br>
<br>
