



## 싱글톤 패턴의 문제점

- 싱글톤 패턴 구현 코드가 번거롭다.
- 클라이언트가 구체 클래스에 의존한다. -> DIP, OCP 위반
- 테스트하기 힘들다.
- 내부 속성 변경, 초기화 어렵다.
- private 생성자이므로 자식 클래스를 만들기 어렵다. -> 유연성이 떨어진다.

<br>
<br>
<br>
<br>

## 싱글톤 컨테이너 (스프링 컨테이너)

- 스프링 컨테이너는 객체 인스턴스를 싱글톤으로 관리한다.
- DIP, OCP, 테스트, private 생성자로 부터 자유로워진다.

<br>

(* 빈 스코프를 이용하여 새로운 객체를 생성해서 반환할 수 있다.)

<br>
<br>
<br>
<br>

## 싱글톤 방식의 주의점

- 무상태 설계해야 한다.
- 특정 클라이언트에 의존적인 필드 X
- 특정 클라이언트가 값을 변경할 수 있는 필드 X
- 가급적 읽기만
- 필드 대신 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용한다.

<br>
<br>
<br>
<br>

## @Configuration과 CGLIB

- 스프링이 자바 코드까지 조작하기는 어렵다.
- @Configuration을 적용하면 스프링은 클래스의 바이트코드를 조작하는 라이브러리(CGLIB)를 사용한다.

<br>

CGLIB를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한다.

아래와 같이 동작한다고 볼 수 있다.

```java
@Bean
public MemberRepository memberRepository() {
    
    if (memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있다면) {
        return 스프링 컨테이너에서 찾아서 반환;    
    } else {
        // 스프릥 컨테이너에 없다면
        기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
        return 반환;
    }
}
```

<br>

이러한 동작이 이루어지며 싱글톤이 보장됩니다.
(* 실제로 CGLIB 내부는 훨씬 더 복잡합니다.)

<br>
<br>
<br>
<br>

## 프로토타입 빈

- 스프링 컨테이너 요청 할 때 마다 새로 생성
- 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입 그리고 초기화까지만 관여
- 종료 메서드 호출 x
- 프로토타입 빈은 프로토타입 빈을 조회한 클라이언트가 관리
- 종료 메서드에 대한 호출도 클라이언트가 직접한다.

<br>
<br>

### 프로토타입 빈 사용하는 경우

- 매번 사용할 때 마다 의존관계 주입이 완료된 새로운 객체가 필요할 때 사용
- ObjectProvider, JSR330 Provider 등은 프로토타입 뿐만 아니라 DL이 필요한 경우는 언제든지 사용할 수 있다.

<br>
<br>
<br>
<br>

## 웹 스코프

- 웹 환경에서만 동작
- 프로토타입과 다르게 스프링이 해당 스코프의 종료시점까지 관리한다. -> 종료 메서드 호출

<br>
<br>

### 웹 스코프 종류

- request
- session
- application
- websocket

<br>
<br>

### request Scope

<br>

1. Provider를 사용한다.
2. 프록시를 사용한다.

<br>

아래 코드는 프록시를 사용하였습니다.

```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String ruquestURL) {
        this.requestURL = requestURL;
    }
    
    // ...
}
```

<br>

- @Scope에 proxyMode = ScopedProxyMode.TARGET_CLASS 를 설정하면 스프링 컨테이너는 CGLIB 라이브러리를 이용하여 MyLogger를 가짜 프록시 객체를 생성한다.
- 가짜 프록시 객체는 요청이 오면 그때 내부에서 진짜 빈을 요청한다.