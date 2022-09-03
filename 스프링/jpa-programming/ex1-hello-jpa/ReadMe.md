

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

- 객체의 두 관계중 하나를 연관관계의 주인으로 지정
- 연관관계의 주인만이 외래 키를 관리
- 주인이 아닌 쪽은 읽기만 가능
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

