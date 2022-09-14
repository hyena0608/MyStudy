
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


