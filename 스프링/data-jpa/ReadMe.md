

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