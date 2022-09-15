
# 1. JPQL vs QueryDSL

- QueryDSL은 JPQL 빌더
- JPQL
  - 실행 시점 오류 (문자)
  - 파라미터 바인딩 수동
- QueryDSL
  - 컴파일 시점 오류 (코드)
  - 파라미터 바인딩 자동 처리

<br>
<br>
<br>
<br>

# 2. JPAQueryFactory

```java
@Autowired
EntityManager em;

JPAQueryFactory queryFactory = new JPAQueryFactory(em);
```

- JPAQueryFactory를 생성할 때 제공하는 EntityManager에 달려 있습니다.
- 때문에 트랜잭션 마다 별도의 영속성 컨텍스트를 제공합니다.
- 이는 동시성 문제를 해결해줍니다.

<br>
<br>
<br>
<br>

# 3. Q-Type

- Q클래스 인스턴스 사용법 - 2가지
  - 별칭 직접 지정
  - 기본 인스턴스 사용

```java
QMember qMember = new QMember("m"); // 별칭 직접 지정
QMember qMember = QMember.member; // 기본 인스턴스 사용
```

<br>
<br>
<br>
<br>

# 4. 검색 조건 쿼리

<br>
<br>

## 4-1. 기본 검색 쿼리

```java
Member findMember1 = queryFactory
        .selectFrom(member)
        .where(
                member.username.eq("member1"),
                member.age.eq(10))
        .fetchOne();

Member findMember2 = queryFactory
        .selectFrom(member)
        .where(member.username.eq("member1").and(member.age.eq(10)))
        .fetchOne();
```

### 4-1-1. 검색 조건 메서드 체인

- `.and()`
- `.or()`

<br>
<br>

### 4-1-2. select, from 합칠 수 있다.

- `selectFrom`

<br>
<br>

### 4-1-3. AND 조건 파라미터 처리

```java
.where(
        member.username.eq("member1"),
        member.age.eq(10)
        )
```

- `where()`에 파라미터로 검색 조건 추가 -> `AND` 조건 추가된다.
- `null` 값을 무시해준다.
  - 메서드 추출을 활용해서 동적 쿼리 가능

<br>
<br>
<br>
<br>

# 5. 결과 조회 - fetch

- `fetch()`
  - 리스트 조회
  - 데이터 없을 시 빈 리스트 반환
- `fetchOne()`
  - 단 건 조회
    - 결과 없으면 : null
    - 결과 둘 이상 : com.querydsl.core.NonUniqueResultException
- `fetchFirst()` : `limit(1).fetchOne()`
- `fetchResults()`
  - 페이징 정보 포함
  - total count 쿼리 추가 실행
- `fetchCount()`
  - count 쿼리로 변경해서 count 수 조회

<br>
<br>
<br>
<br>

# 6. 정렬

- desc(), acs()
- nullsLast(), nullsFirst() : null 데이터 순서 부여

```java
List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.eq(100))
        .orderBy(member.age.desc(), member.username.asc().nullsLast())
        .fetch();
```

<br>
<br>
<br>
<br>

# 7. 페이징

- 조회 건수 제한 - offset(), limit()

```java
List<Member> result = queryFactory
        .selectFrom(member)
        .orderBy(member.username.desc())
        .offset(1)
        .limit(2)
        .fetch();

assertThat(result.size()).isEqualTo(2);
```

<br>
<br>

- 전체 조회 수

```java
QueryResults<Member> queryResults = queryFactory
      .selectFrom(member)
      .orderBy(member.username.desc())
      .offset(1)
      .limit(2)
      .fetchResults();

assertThat(queryResults.getTotal()).isEqualTo(4);
assertThat(queryResults.getLimit()).isEqualTo(2);
assertThat(queryResults.getOffset()).isEqualTo(1);
assertThat(queryResults.getResults().size()).isEqualTo(2);
```

- count 쿼리 실행 주의

실무에서 페이징 쿼리를 작성할 때

데이터를 조회하는 쿼리는 여러 테이블을 조회해야 하는데

count 쿼리는 조인이 필요 없는 경우도 있습니다.

때문에 자동화된 count 쿼리는 원본 쿼리와 같이 모두 조인을 해버리기 때문에 성능이 안나올 수 있습니다.

count 쿼리에 조인이 필요없는 성능 최적화가 필요하다면, count 전용 쿼리를 별도로 작성해야 합니다.

<br>
<br>
<br>
<br>

# 8. 집합

<br>
<br>

## 8-1. count(), sum(), avg(), max(), min()

```java
List<Tuple> result = queryFactory
        .select(
                member.count(),
                member.age.sum(),
                member.age.avg(),
                member.age.max(),
                member.age.min()
        )
        .from(member)
        .fetch();

Tuple tuple = result.get(0);
assertThat(tuple.get(member.count())).isEqualTo(4);
assertThat(tuple.get(member.age.sum())).isEqualTo(100);
assertThat(tuple.get(member.age.avg())).isEqualTo(25);
assertThat(tuple.get(member.age.max())).isEqualTo(40);
assertThat(tuple.get(member.age.min())).isEqualTo(10);
```

- JPQL이 모든 집합 함수 제공

<br>
<br>
<br>

## 8-2. groupBy(), having()

```java
List<Tuple> result = queryFactory
        .select(team.name, member.age.avg())
        .from(member)
        .join(member.team, team)
        .groupBy(team.name)
        .fetch();
```

<br>
<br>

having() 사용

```java
.groupBy(item.price)
.having(item.price.gt(1000))
```

<br>
<br>
<br>
<br>

# 9. 조인

- 조인의 기본 문법은 첫 번째 파라미터에 조인 대상을 지정한다
- 두 번째 파라미터에 별칭으로 사용할 Q 타입을 지정한다.

```java
join(조인 대상, 별칭으로 사용할 Q타입)
```

<br>

- `join()`, `innerJoin()`
  - 내부 조인
- `leftJoin()` : left 외부 조인 
- `rightJoin()` : right 외부 조인
- JPQL의 `on`과 성능 최적화를 위한 `fetch` 조인 제공

<br>
<br>
<br>

## 9-1. 세타 조인

- 연관 관계가 없는 필드로 조인

```java
List<Member> result = queryFactory
        .select(member)
        .from(member, team)
        .where(member.username.eq(team.name))
```

<br>
<br>
<br>

## 9-2. 조인 - on절

- ON절 활용
  - 조인 대상 필터링
  - 연관관계 없는 엔티티 외부 조인

<br>
<br>

### 9-2-1. 조인 대상 필터링

```java
/**
 * JPQL : select m, t from Member m left join m.team t on t.name = 'teamA'
 */

List<Tuple> result = queryFactory
        .select(member, team)
        .from(member)
        .leftJoin(member.team, team)
        .on(team.name.eq("teamA"))
        .fetch();
```

<br>

결과

```java
t=[Member(id=3, username=member1, age=10), Team(id=1, name=teamA)]
t=[Member(id=4, username=member2, age=20), Team(id=1, name=teamA)]
t=[Member(id=5, username=member3, age=30), null]
t=[Member(id=6, username=member4, age=40), null]
```

- on절로 조인 대상 필터링할 때 내부 조인이면 where 절에서 필터링 하는 것과 동일
- 내부 조인이면 where을 주로 사용하자.

<br>
<br>

### 9-2-2. 연관관계 없는 엔티티 외부 조인

```java
/**
 * select m, t from Member m LEFT JOIN Team t on m.username = t.name
 */

List<Tuple> result = queryFactory
        .select(member, team)
        .from(member)
        .leftJoin(team)
        .on(member.username.eq(team.name))
        .fetch();
```

<br>

결과

```java
t=[Member(id=3, username=member1, age=10), null]
t=[Member(id=4, username=member2, age=20), null]
t=[Member(id=5, username=member3, age=30), null]
t=[Member(id=6, username=member4, age=40), null]
t=[Member(id=7, username=teamA, age=0), Team(id=1, name=teamA)]
t=[Member(id=8, username=teamB, age=0), Team(id=2, name=teamB)]
```

<br>

- 일반 조인
  - leftJoin(member.team, team)
- on 조인
  - from(member).leftJoin(team).on(xxx)

<br>
<br>
<br>
<br>

## 9-3. 조인 - 페치 조인

- `join()`, 'leftJoin()' 등 조인 기능 뒤에 `fetchJoin()`를 추가한다.

<br>

### 9-3-1. 지연 로딩

- Member, Team SQL 쿼리 각각 실행

```java
Member findMember = queryFactory
        .selectFrom(member)
        .where(member.username.eq("member1"))
        .fetchOne();

boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
Assertions.assertThat(loaded).as("페치 조인 미적용").isFalse();
```

<br>
<br>
<br>

### 9-3-2. 즉시 로딩

- Member, Team SQL 쿼리 조인으로 한번에 조회

```java
Member findMember = queryFactory
        .selectFrom(member)
        .join(member.team, team)
        .fetchJoin()
        .where(member.username.eq("member1"))
        .fetchOne();

boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
Assertions.assertThat(loaded).as("페치 조인 적용").isTrue();
```

<br>
<br>
<br>
<br>

# 10. 서브 쿼리

- `com.querydsl.jpa.JPAExpressions` 를 사용

<br>
<br>

## 10-1. eq 사용

- 엘리어스를 주의한다.

```java
QMember memberSub = new QMember("memberSub");

List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.in(
                JPAExpressions
                        .select(memberSub.age)
                        .from(memberSub)
                        .where(memberSub.age.gt(10))
        ))
        .fetch();
```

<br>
<br>
<br>

## 10-2. select 절 subquery

```java
List<Tuple> fetch = queryFactory
        .select(member.username,
                JPAExpressions
                      .select(memberSub.age.avg())
                      .from(memberSub)
        ).from(member)
        .fetch();
```

<br>
<br>
<br>

## 10-3. JPAExpressions static import 하기

```java
import static com.querydsl.jpa.JPAExpresiions.select;

List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.eq(
                select(memberSub.age.max())
                .from(memberSub)
        ))
        .fetch();
```

<br>
<br>
<br>

## 10-4. from 절의 서브쿼리 한계

- JPA JPQL, Querydsl : from절의 서브쿼리는 지원하지 않는다.
- 해결 방안
  - 하이버네이트 구현체를 사용한다
  - 서브쿼리를 join으로 변경
  - 애플리케이션에서 쿼리를 2번 분리
  - nativeSQL 사용

<br>
<br>
<br>
<br>

# 11. Case 문

- select, where, order by 에서 사용한다.

<br>
<br>

## 11-1. 기본 조건문

```java
List<String> result = queryFactory
        .select(member.age
                .when(10).then("열살")
                .when(20).then("스무살")
                .otherwise("기타"))
        .from(member)
        .fetch();
```

<br>
<br>
<br>

## 11-2. 복잡한 조건문 - CaseBuilder

```java
List<String> result = queryFactory
        .select(new CaseBuilder()
                .when(member.age.between(0, 20)).then("0~20살")
                .when(member.age.between(21, 30)).then("21~30살")
                .otherwise("기타"))
        .from(member)
        .fetch();
```

<br>
<br>
<br>

## 11-3. orderBy + Case

- Querydsl는 rankPath처럼 복잡한 조건을 변수로 선언해서 select, orderBy절에서 사용할 수 있다.
  - 자바 코드로 작성하기 때문에 가능하다.

- 다음과 같은 임의의 순서로 회원 출력할 때
  - 0~30살이 아닌 회원 가장 먼저 출력
  - 0~20살 회원 출력
  - 21~30살 회원 출력

<br>

```java
NumberExpression<Integer> rankPath = new CaseBuilder()
        .when(member.age.between(0, 20)).then(2)
        .when(member.age.between(21, 30)).then(1)
        .otherwise(3);

List<Tuple> result = queryFactory
        .select(member.username, member.age, rankPath)
        .from(member)
        .orderBy(rankPath.desc())
        .fetch();
```

<br>
<br>
<br>
<br>

# 12. 상수, 문자

<br>

## 12-1. Expressions.constant(xxx)

- 상수가 필요할 때 사용한다.

```java
Tuple result = queryFactory
        .select(member.username, Expressions.constant("A"))
        .from(member)
        .fetchFirst();
```

<br>
<br>
<br>

## 12-2. concat - `stringValue()`

- `stringValue()`
  - 문자가 아닌 다른 타입들을 문자로 변환할 수 있다.
  - ENUM을 처리할 때도 자주 사용한다.

```java
String result = queryFactory
        .select(member.username.concat("_").concat(member.age.stringValue()))
        .from(member)
        .where(member.username.eq("member1"))
        .fetchOne();
```

<br>
<br>
<br>
<br>

# 13. 프로젝션 - 튜플

<br>

## 13-1. 프로젝션 대상이 하나

- 타입을 명확하게 지정할 수 있다.

```java
List<String> result = queryFactory
        .select(member.username)
        .from(member)
        .fetch();
```

<br>
<br>
<br>

## 13-2. 튜플 조회 - 프로젝션 대상 둘 이상

```java
List<Tuple> result = queryFactory
        .select(member.username, member.age)
        .from(member)
        .fetch();

for (Tuple tuple : result) {
    String username = tuple.get(member.username);
    Integer age = tuple.get(member.age);
}
```

<br>
<br>
<br>
<br>

# 14. DTO 조회

- MemberDto

```java
@Data
@NoArgsConstructor
public class MemberDto {

  private String username;
  private int age;

  public MemberDto(String username, int age) {
    this.username = username;
    this.age = age;
  }
}
```

<br>
<br>

## 14-1. 순수 JPA에서 DTO 조회

- new 명령어 사용
- DTO의 package 이름 적어야한다.
- 생성자 방식만 지원한다.

```java
List<MemberDto> result = em.createQuery(
        "select new study.querydsl.dto.MemberDto(m.username, m.age) " +
                "from Member m", MemberDto.class)
        .getResultList();
```

<br>
<br>
<br>

## 14-2. 프로젝션 결과 반환 - Querydsl 빈 생성

- DTO 반환할 때 사용
  - 프로퍼티 접근
  - 필드 직접 접근
  - 생성자 사용

<br>
<br>
<br>

### 14-2-1. 프로퍼티 접근 - `Projections.bean()`

```java
List<MemberDto> result = queryFactory
        .select(Projections.bean(MemberDto.class,
                member.username,
                member.age))
        .from(member)
        .fetch();
```

<br>
<br>
<br>

### 14-2-2. 필드 직접 접근 - `Projections.fields()`

```java
List<MemberDto> result = queryFactory
        .select(Projections.fields(MemberDto.class,
                member.username,
                member.age))
        .from(member)
        .fetch();
```

<br>
<br>
<br>

### 14-2-3. 별칭이 다를 때 - `member.username.as("name")`

- ExpressionUtils.as(source, alias)
  - 필드나, 서브쿼리에 별칭 적용
- `username.as("memberName")`
  - 필드에 별칭 적용

- 프로퍼티나, 필드 접근 생성 방식에서 이름이 다를 때 해결한다.

```java
List<UserDto> result = queryFactory
        .select(Projections.fields(UserDto.class,
                member.username.as("name"),
                
                ExpressionUtils.as(
                        JPAExpressions
                          .select(memberSub.age.max())
                          .from(memberSub), "age")
                ))
        .from(member)
        .fetch();
```

<br>
<br>
<br>

### 14-2-4. 생성자 사용 - `Projections.constructor()`

```java
List<MemberDto> result = queryFactory
        .select(Projections.constructor(MemberDto.class,
                member.username,
                member.age))
        .from(member)
        .fetch();
```

<br>
<br>
<br>
<br>

## 14-3. 프로젝션 결과 반환 - `@QueryProjection`

- 생성자 + @QueryProjection

<br>
<br>

### 14-3-1. 먼저 QMemberDto를 생성해야 한다.

- `@QueryProjection`을 생성자에 추가해준다.
- `./gradlew compileQuerydsl`
- `QMemberDto` 생성을 확인한다.

```java
@Data
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;

    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
```

<br>
<br>
<br>

### 14-3-2. @QueryProjection 활용

- 장점
  - 컴파일러로 타입 체크를 할 수 있다.
  - 안전한 방법
- 단점
  - DTO에 QueryDSL 어노테이션을 유지해야 한다.
  - DTO까지 Q 파일을 생성해야 한다.

<br>

```java
List<MemberDto> result = queryFactory
        .select(new QMemberDto(member.username, member.age))
        .from(member)
        .fetch();
```

<br>
<br>
<br>
<br>

# 15. 동적 쿼리 - `BooleanBuilder`, `Where` 다중 파라미터

- 동적 쿼리를 해결하는 두가지 방식
  - BooleanBuilder
  - Where 다중 파라미터

<br>
<br>

## 15-1. 동적 쿼리 - BooleanBuilder

```java
public void 동적쿼리_BooleanBuilder() throws Exception{
    String usernameParam="member1";
    Integer ageParam=10;

    List<Member> result = searchMember(usernameParam,ageParam);
}

private List<Member> searchMember(String usernameCond, Integer ageCond) {
    BooleanBuilder builder = new BooleanBuilder();
    
    if (usernameCond != null) {
        builder.and(member.username.eq(usernameCond));
    }
    
    if (ageCond != null) {
        builder.and(member.age.eq(ageCond));
    }
    
    return queryFactory
        .selectFrom(member)
        .where(builder)
        .fetch();
}
```

<br>
<br>
<br>

## 15-2. 동적 쿼리 - Where 다중 파라미터 사용

- where 조건에 null 값은 무시된다.
- 메서드를 다른 쿼리에서도 재활용 할 수 있다.
- 쿼리 자체의 가독성이 높아진다.

<br>

```java
public void 동적쿼리_WhereParam() throws Exception {
    String usernameParam="member1";
    Integer ageParam=10;

    List<Member> result = searchMember(usernameParam,ageParam);
}

private List<Member> searchMember(String usernameCond, Integer ageCond){
    return queryFactory
        .selectFrom(member)
        .where(usernameEq(usernameCond), ageEq(ageCond))
        .fetch();
}

private BooleanExpression usernameEq(String usernameCond) {
    return usernameCond != null ? member.username.eq(usernameCond) : null; 
}

private BooleanExpression ageEq(Integer ageCond) {
    return ageCond != null ? member.age.eq(ageCond) : null;
}
```

<br>
<br>
<br>
<br>

# 16. 수정, 삭제 벌크 연산

- 벌크 연산 시 주의 할 점
  - 영속성 컨텍스트에 있는 엔티티를 무시하고 실행된다.
  - 배치 쿼리를 실행하고 나면 영속성 컨텍스틀 초기화하는 것이 안전

```java
em.flush();
em.clear();
```

<br>
<br>

## 16-1. 쿼리 한번으로 대량 데이터 수정

```java
queryFactory
        .update(member)
        .set(member.username, "비회원")
        .where(member.age.lt(20))
        .execute();
```

<br>
<br>
<br>

## 16-2. 기존 숫자에 1 더하기

```java
queryFactory
        .update(member)
        .set(member.age, member.age.add(1))
        .execute();
```

<br>
<br>
<br>

## 16-3. 쿼리 한번으로 대량 데이터 삭제

```java
queryFactory
        .delete(member)
        .where(member.age.lt(10))
        .execute();
```

<br>
<br>
<br>
<br>

# 17. SQL function

- JPA와 같이 Dialect에 등록된 내용만 호출할 수 있다.

<br>

```java
String result = queryFactory
        .select(
                Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2}",
                        member.username, "member", "M"))
        .from(member)
        .fetchFirst();
```

<br>
<br>

- querydsl 내장하는 것들이 있다.
  - ansi 표준 함수

```java
queryFactory
        .select(member.username)
        .from(member)
        .where(member.username.eq(member.username.lower()))
        .fetch();
```