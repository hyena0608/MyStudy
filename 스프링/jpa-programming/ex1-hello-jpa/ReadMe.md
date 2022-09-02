

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
