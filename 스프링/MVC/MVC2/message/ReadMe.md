
## 1. 스프링 메시지 소스 설정

- Interface MessageSource를 통해 메시지 관리 기능 사용
- ResuourceBundleMessageSource 구현체를 스프링 빈으로 등록

<br>
<br>

### 1-1.직접 등록 (스프링부트 사용 시 자동 등록)

- basenames
  -설정 파일의 이름 지정
  - messages.properties, messages_en.properties
    - 파일명 마지막에 언어 정보를 준다.
  - 파일 위치
    - /resources/messages.properties

```java
@Bean
public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages", "errors");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}
```

<br>
<br>
<br>

### 1-2. 스프링 부트 메시지 소스 기본 값

```java
spring.messages.basename=messages
```

<br>
<br>
<br>

### 1-3. 메시지 파일 만들기

`/resources/messages.properties`

```properties
label.item=상품
label.item.id=상품 ID
label.item.itemName=상품명
label.item.price=가격
label.item.quantity=수량
```

<br>

`/resources/messages_en.properties`

```properties
label.item=Item
label.item.id=Item ID
label.item.itemName=Item Name
label.item.price=price
label.item.quantity=quantity
```

<br>
<br>
<br>

### 1-4. 스프링 메시지 소스 사용

- MessageSource 인터페이스

```java
@Autowired MessageSource ms;

@Test
void test(){
    String result=ms.getMessage("hello",null,null);
    assertThat(result).isEqualTo("안녕");
    
    assertThatThrownBy(()->ms.getMessage("no_code",null,null))
    .isInstanceOf(NoSuchMessageException.class);
    
    String result=ms.getMessage("co_code",null,"기본 메시지",null);
    assertThat(result).isEqualTo("기본 메시지");
    
    String message=ms.getMessage("hello.name",new Object[]{"Spring"},null);
    assertThat(message).isEqualTo("안녕 Spring");
    
    assertThat(ms.getMessage("hello",null,null)).isEqualTo("안녕");
    assertThat(ms.getMessage("hello",null,Locale.KOREA)).isEqualTo("안녕");
    
    assertThat(ms.getMessage("hello",null,Locale.ENGLISH)).isEqualTo("hello");
}            
```

<br>
<br>
<br>

### 1-5. 타임리프 메시지 적용

- 타임리프의 메시지 표현식 `#{...}`를 사용한다.

<br>

- 렌더링 전

```java
<div th:text=#{label.item}"></div>
```

- 렌더링 후

```java
<div>상품</div>
```

- 파라미터 사용 법

```java
<p th:text="#{hello.name(#{item.itemName})}"></p>
```

<br>
<br>
<br>

### 1-6. 웹 애플리케이션 국제화

- 이미 작업은 끝나 있는 상태이다.
- `#{...}`를 통해서 사용해놨기 때문이다.

<br>
<br>
<br>

### 1-7. 스프링의 국제화 메시지 선택

메시지 기능은 Locale 정보를 알아야 언어를 선택한다.

스프링의 기본 언어 선택은 `Accept-Language` 헤더의 값을 사용한다.

<br>
<br>
<br>

### 1-8. LocalResolver

- 스프링은 Locale 선택 방식을 변경할 수 있다.
  - LocaleResolver 인터페이스를 제공한다.
- 스프링 부트는 기본으로 `Accept-Language`를 활용하는 `AcceptHeaderLocaleResolver`를 사용한다.

<br>

- Locale 선택 방식 변경
  - LocaleResolver의 구현체를 변경한다.
  - 쿠키나 세션 기반의 Locale 선택 기

<br>
<br>
<br>