# 목차

- [개요](#개요)
- [1. 주석을 최대한 쓰지 말자](#1-주석을-최대한-쓰지-말자)
  * [주석은 나쁜 코드를 보완하지 못한다.](#주석은-나쁜-코드를-보완하지-못한다)
- [좋은 주석](#좋은-주석)
- [주석보다 annotation](#주석보다-annotation)
- [JavaDoc](#javadoc)
    + [Class level](#class-level)
    + [Filed level](#filed-level)
    + [Method level](#method-level)

---

# 개요

주석은 좋은게 아니다.

코드가 불분명할 때 사용하기 때문이다.

주석을 쓰기보다 코드를 한번 더 점검해보자

그렇지만 쓸거면 정말 좋은 주석을 사용하자.

근데 요즘은 주석을 어노테이션으로 대체하기도 한다.



# 1. 주석을 최대한 쓰지 말자

## 주석은 나쁜 코드를 보완하지 못한다.

> 코드에 주석을 추가하는 이유는 *코드 품질이 나쁘기 때문이다.*

주석으로 설명하지 말고 개선하는데 시간을 사용하자.

코드로도 의도를 충분히 설명할 수 있다.

밑에 예시를 봐보자

```java
// 강아지가 간식을 먹을 자격이 있는지 검사한다.
if ((dog.talent & COUNT_TALENT) dog.age > 7)

// 의미있는 이름으로 지어서 해결하자
if (dog.isTalentGreat())
```

# 좋은 주석

> 구현에 대한 정보를 제공한다.

- 정규식같은 경우는 한눈에 이해하기가 어려워서 주석을 써도 좋다.
```java
// kk:mm:ss EEE, MMM dd, yyyy 형식
Pattern timeFormat =
    Pattern.compile("\\d*:\\d:\\d* \\w*, \\w* \\d* \\d*");
```

- 의도와 중요성을 설명하는 것도 좋은 주석이다.
```java
// 스레드 많이 생성해서 시스템 영향을 끼쳐 테스트하기
for (int i = 0; i < 25000; i++) {
    SomeThread someThread = ThreadBuilder.build().build();
}

// 물론 이렇게 메서드로 만들어 주석이 필요 없게 할 수 있다.
makeManyThreadstoEfftectSystem();
```

- TODO, FIXME 주석
    - TODO : 앞으로 할 일, 지금은 해결하지 않지만 나중에 해야할 일을 미리 적어둘 때
    - FIXME : 문제가 있지만, 당장 수정할 필요는 없을 때. 가능하면 빨리 수정하는게 좋다.
```java
// TODO : 기능 만들기

// FIXME : 파라미터명 바꾸기 
```

# 주석보다 annotation

`java.lang.annotation`

>`annotation` = 코드에 대한 메타데이터
>- 코드의 실행 흐름에 간섭을 주기도 하고, 주석처럼 코드에 대한 정보를 줄 수 있다.

`@Deprecated` : 컴파일러가 warning을 발생시킴. IDE에서 사용시 표시됨

`@NotThreadSafe` : Thread Safe 하지 않음을 나타냄

# JavaDoc

> Java 코드에서 API 문서를 HTML 형식으로 생성해주는 도구

자바 문서화를 해주어 clear하게 볼 수 있게 해준다.

### Class level

```java
/**
 * Hero is the main entity we'll be using to ...
 *
 * Please ...
 * @author hyena
 */
 public class SuperHero extends Person {
     //...
 }
```

### Filed level

```java
/**
 * The public name of a hero that is common knowledge
 */
 private String heroName;
```

### Method level

```java
/**
 * This is a simple description of the method ...
 * @param ...
 * @return ...
 * @see ...
 * @since 1.0
 */
 public int successfullyAttacked(int incomingDamage) {
     // do something
     return 0;
 }
```
