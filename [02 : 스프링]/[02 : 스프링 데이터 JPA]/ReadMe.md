

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

## 9. 벌크성 수정 쿼리

- 벌크성 수정, 삭제 쿼리는 @Modifying 어노테이션을 사용 
- 벌크성 쿼리를 실행하고 나서 영속성 컨텍스트 초기화: @Modifying(clearAutomatically = true)
  - 이 옵션 없이 회원을 findById로 다시 조회하면 영속성 컨텍스트에 과거 값이 남아서 문제가 될 수 있다.

```java
@Modifying(clearAutomatically = true)
@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
int bulkAgePlus(@Param("age") int age);
```

<br>
<br>
<br>
<br>

## 10. @EntityGraph

- 연관된 엔티티들을 SQL 한번에 조회하는 방법

보통 N + 1 문제가 발생하면 페치 조인으로 해결하는 경우가 많다.

<br>
<br>
<br>

### 10-1. JPQL 페치 조인

```java
Query("select m from Member m left join fetch m.team")
List<Member> findMemberFetchJoin();
```

<br>
<br>
<br>

### 10-2. EntityGraph (JPQL 없이 페치 조인)

- 스프링 데이터 JPA는 JPA가 제공하는 EntityGraph 기능을 편리하게 사용하게 도와준다.
- 이 기능을 사용하면 JPQL 없이 페치 조인을 사용할 수 있다.

```java
// 공통 메서드 오버라이드
@Override
@EntityGraph(attributePaths = {"team"})
List<Member> findAll();

// JPQL + 엔티티 그래프
@EntityGraph(attributePaths = {"team"})
@Query("select m from Member m")
List<Member> findMemberEntityGraph();

// 메서드 이름으로 쿼리에서 특히 편리하다.
@EntityGraph(attributePaths = {"team"})
List<Member> findEntityGraphByUsername(@Param("username") String username);
```

<br>

- EntityGraph 정리
  - 페치 조인의 간편 버전
  - LEFT OUTER JOIN 사용

<br>
<br>
<br>

### 10-3. NamedEntityGraph 사용 방법

- 잘 사용안하지만 있는 기능

```java
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
@Entity
public class Member {}

@EntityGraph("Member.all")
@Query("select m from Member m")
List<Member> findMemberEntityGraph();
```

<br>
<br>
<br>
<br>

## 11. JPA Hint

- JPA 구현체에게 제공하는 힌트
- org.springframework.data.jpa.repository.QueryHints 어노테이션을 사용
- 만약 읽기만 하고 싶으면 다음과 같이 JPA에게 힌트를 준다.

<br>

```java
@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
Member findReadOnlyByUsername(String username);
```

```java
public void queryHint() {
    // given
    Member member1 = memberRepository.save(new Member("member1", 10));
    entityManager.flush();
    entityManager.clear();

    // 더티 체크 하지 않는다. -> readOnly
    Member findMember = memberRepository.findReadOnlyByUsername("member1");
    findMember.setUsername("member2");

    entityManager.flush();
}
```

<br>
<br>
<br>
<br>

## 12. 사용자 정의 리포지터리 구현

- 스프링 데이터 JPA 리포지터리는 인터페이스만 정의하고 구현체는 스프링이 자동 생성한다.
- 스프링 데이터 JPA가 제공하는 인터페이스를 직접 구현하기 어렵다.
- 인터페이스의 메서드를 직접 구현하고 싶을 때 사용한다.
  - JPA 직접 사용 (EntityManager)
  - 스프링 JDBC Template
  - MyBatis
  - 데이터베이스 커넥션 직접 사용
  - QueryDSL

<br>
<br>

- 사용자 정의 인터페이스
```java
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
```

<br>

- 사용자 정의 인터페이스 구현 클래스

```java
@RequiredArgsContructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    
    private final EntityManager em;
    
    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
```

<br>

- 사용자 정의 인터페이스 상속

```java
public interface MemberRepository 
        extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    
}
```

<br>

- 사용자 정의 메서드 호출 코드

```java
List<Member> result  = memberRepository.findMemberCustom();
```

<br>
<br>
<br>

### 12-1. 사용자 정의 구현 클래스

  - 규칙 : 리포지터리 인터페이스 이름 + Impl
  - 스프링 데이터 JPA가 인식해서 스프링 빈으로 등록

<br>
<br>
<br>

### 12-2. Impl 대신 다른 이름으로 변경하려면

- XML설정

```xml

<repositories base-package="study.datajpa.repository" repository-impl-postfix="Impl" />
```

<br>

- JavaConfig 설정

```java
@EnableJpaRepository(basePackages = "study.datajpa.repository", repositoryImplementationPostfix = "Impl")
```

<br>
<br>
<br>

### 12-3. 사용자 정의 리포지터리 참고

- 실무 : QueryDSL, SpringJDBCTemplate을 함께 사용할 때 사용자 정의 리포지터리 기능을 자주 사용함
- 항상 사용자 정의 리포지터리가 필요하진 않다.
  - 임의의 리포지터리를 만들어도 된다.
  - MemberQueryRepository를 인터페이스가 아닌 클래스로 만들고 스프링 빈으로 등록해서 그냥 직접 사용해도 된다.
    - 이 경우 스프링 데이터 JPA와는 관계없이 작동한다.

<br>
<br>
<br>
<br>

## 13. Auditing

- 등록일
- 수정일
- 등록자
- 수정자

<br>
<br>

### 13-1. 순수 JPA 사용

- @PrePersist, @PostPersist
- @PreUpdate, @PostUpdate 

```java
@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}

public class Member extends JpaBaseEntity {}
```

<br>
<br>
<br>

### 13-2. 스프링 데이터 JPA 사용

- 설정
  - @EnableJpaAuditing -> 스프링 부트 설정 클래스에 적용
  - @EntityListeners(AuditingEntityListener.class) -> 엔티티에 적용
- 사용 어노테이션
  - @CreatedDate
  - @LastModifiedDAte
  - @CreatedBy
  - @LastModifiedBy

<br>

- 등록자 수정자를 처리해주는 AuditorAware을 스프링 빈에 등록해야 한다.

```java
@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedBy
  private String lastModifiedBy;
}

public class Member extends BaseEntity {}
```

<br>
<br>
<br>

### 13-3. 실무 사용 예시

- 실무에서 대부분의 엔티티는 등록시간, 수정시간이 필요하지만 등록자, 수정자는 없을 수도 있다.
- 그래서 다음과 같이 Base 타입을 분리하고, 원하는 타입을 선택해서 상속한다.

```java
public class BaseTimeEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
}

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}

```

<br>
<br>
<br>

### 13-4. 전체 적용

- @EntityListeners(AuditingEntityListener.class) 를 생략하고 스프링 데이터 JPA가 제공하는 이벤트를 엔티티 전체에 적용하려면 orm.xml에 다음과 같이 등록한다.

```xml
<?xml version=“1.0” encoding="UTF-8”?>
<entity-mappings xmlns=“http://xmlns.jcp.org/xml/ns/persistence/orm”
                 xmlns:xsi=“http://www.w3.org/2001/XMLSchema-instance”
                 xsi:schemaLocation=“http://xmlns.jcp.org/xml/ns/persistence/
orm http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd”
                 version=“2.2">
    <persistence-unit-metadata>
        <persistence-unit-defaults>
            <entity-listeners>
                <entity-listener
class="org.springframework.data.jpa.domain.support.AuditingEntityListener”/>
            </entity-listeners>
        </persistence-unit-defaults>
    </persistence-unit-metadata>
</entity-mappings>
```

<br>
<br>
<br>
<br>

## 14. 도메인 클래스 컨버터

- HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩

<br>
<br>

### 14-1. 도메인 클래스 컨버터 사용 전

```java
@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @GetMapping("/members/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUsername();
  }
}
```

<br>
<br>
<br>

### 14-2. Web 확장 - 도메인 클래스 컨버터 사용 후

```java
@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;
  
  @GetMapping("/members2/{id}")
  public String findMember2(@PathVariable("id") Member member) {
    return member.getUsername();
  }
}
```

- HTTP 요청은 회원 id를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
- 도메인 클래스 컨버터도 리포지터리를 사용해서 엔티티를 찾는다.
- 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다.
  - 트랜잭션이 없는 범위에서 엔티티를 조회했으므로, 엔티티를 변경해도 DB에 반영되지 않는다.

<br>
<br>
<br>
<br>

## 15. Web 확장 - 페이징과 정렬
e
- 스프링 데이터가 제공하는 페이징과 정렬 기능을 스프링 MVC

```java
@GetMapping("/members")
public Page<Member> list(Pageable pageable) {
    Page<Member> page = new memberRepository.findAll(pageable);
    return page;
}
```

- 파라미터로 Pageable 사용 가능
- Pageable은 <<interface>> 이며 org.springframework.data.domain.PageRequest 객체를 생성한다.

<br>
<br>

### 15-1. 페이징 요청 파라미터

- ex) `/members?page=0&size=3&sort=id,desc&sort=username,desc`
  - page : 현재 페이지, 0부터 시작
  - size : 한 페이지에 노출할 데이터 건수
  - sort : 정렬 조건을 정의한다.

<br>
<br>
<br>

### 15-2. 기본값

- 글로벌 설정 : 스프링부트

```java
spring.data.web.pageable.default-page-size=20
spring.data.web.pageable.max-page-size=2000
```

<br>
<br>
<br>

### 15-3. 개별 설정

- @PageableDefault 어노테이션 사용

```java
@RequestMapping(value = "/members_page", method = RequestMethod.GET)
public String list(@PageableDefault(size = 12, sort = "username",
        direction = Sort.Direction.DESC) Pageable pageable) {
        ...
}
```

<br>
<br>
<br>

### 15-4. 접두사

- 페이징 정보가 둘 이상이면 접두사로 구분한다.
- @Qualifier에 접두사명 추가 "{접두사명}_xxx"
- ex) /members?member_pate=0&order_page=1

```java
public String list(
        @Qualifier("member") Pageable memberPageable,
        @Qualifier("order") Pageable orderPageable, ...
        )
```

<br>
<br>
<br>

### 15-5. Page -> DTO 변환

- Page는 map()을 지원해서 내부 데이터를 다른 것으로 변경할 수 있다.

```java
@Data
public class MemberDto {
    private Long id;
    private String username;
    
    public MemberDto(Member m) {
        this.id = m.getId();
        this.username = m.getUsername();
    }
}

@GetMapping("/members")
public Page<MemberDto> list(Pageable pageable) {
    return memberRepository.findAll(pageable).map(MemberDto::new);
}
```

<br>
<br>
<br>
<br>

## 16. 스프링 데이터 JPA 구현체 분석

- 스프링 데이터 JPA가 제공하는 공통 인터페이스의 구현체
- org.springframework.data.jpa.repository.support.SimpleJpaRepository

<br>
<br>

### 16-1. SimpleJpaRepository를 살펴보자

```java
@Repository
@Transactional(readOnly = true)
public class SimpleJpaRepository<T, ID> ... {
    
    @Transactional
    public <S extends T> S save(S entity) {
        
        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
```

- @Repository 적용
  - JPA 예외를 스프링이 추상화한 Exception으로 변환
    - 스프링에서 쓸 수 있는 Exception으로 변환
- @Transactional 트랜잭션 적용
  - JPA의 모든 변경은 트랜잭션 안에서 동작
  - 스프링 데이터 JPA는 변경 메서들르 트랙잭션 처리
  - 서비스 계층에서 트랜잭션을 시작하지 않으면 리포지터리에서 트랜잭션 시작
  - 서비스 계층에서 트랜잭션을 시작하면 리포지터리는 해당 트랜잭션을 전파 받아서 사용
  - 스프링 데이터 JPA를 사용할 때 트랜잭션이 없어도 데이터 등록, 변경이 가능
    - 트랜잭션이 리포지터리 계층에 걸려있기 때문이다.

<br>
<br>
<br>

### 16-2. Transactional 성능 향상

- @Transactional(readOnly = true)
  - 데이터를 단순히 조회만하고 변경하지 않은 트랜잭션에서 readOnly = true 옵션을 사용하면 flush를 생략해서 약간의 성능 향상을 얻을 수 있다.

<br>
<br>
<br>
<br>

## 17. 새로운 엔티티를 구별하는 방법

<br>
<br>

### 17-1. *save() 메서드*

- 새로운 엔티티면 저장( persist )
- 새로운 엔티티가 아니면 병합( merge )
  - DB Select 해서 가져온다음에 덮어쓴다.
  - 변경을 할 때 merge가 아닌 "변경 감지"를 이용하는 것이 정석이다.

<br>
<br>
<br>

### 17-2. 새로운 엔티티를 판단하는 기본 전략

- 식별자가 객체일 때 null로 판단
- 식별자가 자바 기본 타입일 때 0으로 판단
- Persistable 인터페이스를 구현해서 판단 로직 변경 가능하다.

<br>

```java
public interface Persistable<ID> {
    ID getId();
    boolean isNew();
}
```

<br>

- JPA 식별자 생성 전략이 @GeneratedValue면 
  - save() 호출 시점에 식별자가 없으므로 새로운 엔티티로 인식해서 정상 동작한다.
- JPA 식별자 생성 전략이 @Id만 사용해서 직접 할당이면 
  - 이미 식별자 값이 있는 상태로 save()를 호출한다.
  - 이 경우 merge()가 호출된다.
    - merge()는 DB를 호출해서 값을 확인하고, DB에 값이 없으면 새로운 엔티티로 인지하므로 비효율적이다.
  - 따라서 Persistable를 사용해서 새로운 엔티티 확인 여부를 직접 구현하는게 효과적이다.
    - @CreatedDate을 조합해서 사용하면 새로운 엔티티 여부를 편리하게 확인할 수 있다.

<br>
<br>

```java
public class Item implements Persistable<String> {
    
    @Id
    private String id;
    
    @CratedDate
    private LocalDate createdDate;
    
    public Item(String id) {
        this.id = id;
    }
    
    @Override
    public String getId() {
        return;
    }
    
    @Override
    public isNew() {
        return createdDate == null;
    }
}
```

<br>
<br>
<br>
<br>

## 18. Projections

- 엔티티 대신에 DTO를 편리하게 조회할 때 사용

<br>
<br>

- 조회할 엔티티의 필드를 getter 형식으로 지정
  - 해당 필드만 선택해서 조회된다.
- 메서드 이름은 자유
- 반환 타입으로 인지한다.

<br>
<br>
<br>

### 18-1. 인터페이스 기반 Closed Projections

- 프로퍼티 형식(getter)의 인터페이스를 제공하면, 구현체는 스프링 데이터 JPA가 제공한다.

```java
public interface UsernameOnly {
    String getUsername();
}

public interface MemberRepository .. {
    List<UsernameOnly> findProjectionsByUsername(String username);
}

@Test
public void projections() .. {
    
    List<UsernameOnly> result = memberRepository.findProjectionsByUsername("m1");
}
```

SQL문은 다음과 같다.

```sql
select m.username from member m where m.username = 'm1';
```

<br>
<br>
<br>

### 18-2. 인터페이스 기반 Open Projections

- 스프링의 SpEL 문법 지원
- SpEL 문법을 사용하면, DB에서 엔티티 필드를 다 조회해온 다음에 계산한다.
- 즉, JPQL SELECT 절 최적화가 되지 않는다.

```java
public interface UsernameOnly {
    @Value("#{target.username + ' ' + target.age}")
  String getUsername();
}
```

<br>
<br>
<br>

### 18-3. 클래스 기반 Projection

- 인터페이스가 아닌 구체적인 DTO 형식도 가능
- 생성자의 파라미터 이름으로 매칭

```java
public class UsernameOnlyDto {
    
    private final String username;
    
    public UsernameOnlyDto(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
}
```

<br>
<br>
<br>

### 18-4. 동적 Projections

- Generic type을 주면, 동적 프로젝션 데이터 변경 가능하다.

```java
<T> List<T findProjectionsByUsername(String username, Class<T> type);
```

<br>
<br>
<br>

### 18-5. 중첩 구조 처리

```java
public interface4 NestedClosedProjection {
    
    String getUsername();
    TeamInfo getTeam();
    
    interface TeamInfo {
        String getName();
    }
}
```

```sql
select
   m.username as col_0_0_,
   t.teamid as col_1_0_,
   t.teamid as teamid1_2_,
   t.name as name2_2_
from
    member m
left outer join
   team t
   on m.teamid=t.teamid
where
    m.username=?
```

<br>
<br>
<br>

### 18-6. 정리

- 프로젝션 대상이 root 엔티티면 유용
- 프로젝션 대상이 root 엔티티를 넘어가면 JPQL SELECT 최적화가 안된다
- 실무의 복잡한 쿼리 해결 한계
- 단순할 때만 사용, 복잡해지면 QueryDSL