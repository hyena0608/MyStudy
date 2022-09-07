
## 1. @PathVariable (경로 변수) 사용

- HTTP API는 리소스 경로에 식별자를 넣는 스타일 선호
  - /mapping/userA
  - /users/1
- @RequestMapping은 URL 경로를 템플릿화 할 수 있다.
  - @PathVariable을 사용하면 매칭 되는 부분을 편리하게 조회할 수 있다.
- @PathVariable의 이름과 파라미터 이름이 같으면 생략할 수 있다.

```java
/**
* PathVariable 사용
* 변수명이 같으면 생략 가능
* @PathVariable("userId") String userId -> @PathVariable userId 
*/
@GetMapping("/mapping/{userId}")
public String mappingPath(@PathVariable("userId") String data) {
  log.info("mappingPath userId={}", data);
  return "ok";
}


@GetMapping("/mapping/users/{userId}/orders/{orderId}")
public String mappingPath2(@PathVariable String userId, @PathVariable Long orderId) {
    log.info("mappingPath userId={}, orderId={}", userId, orderId);
    return "ok";
}
```

<br>
<br>
<br>
<br>

## 2.HTTP 요청 파라미터 - @RequestParam

- 스프링이 제공하는 @RequestParam을 사용하면 요청 파라미터를 쉽게 사용할 수 있다.
- @RequestParam 사용 : 파라미터 이름을 바인딩
- @RequestBody 사용 : View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
- String, int, Integer 등의 단순 타입이면 @RequestParam 생략 가능하다.

```java
@ResponseBody
@RequestMapping("/request-param")
public String requestParam(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge) {
        
    log.info("username={}, age={}", memberName, memberAge);   
    return "ok";
}
```

<br>

@RequestParam("username") String memberName 은 결국 request.getParameter("username")이다.

<br>
<br>
<br>

### 2-1. 파라미터 필수 여부

- @RequestParam(required = true)
  - 파라미터 필수 여부
  - 기본값이 파라미터 필수(true)
- 주의
  - 파라미터 이름만 사용 - 빈문자가 들어온다.
    - /request-param?username=
  - 기본형 null은 안된다.
    - @RequestParam(required = false) int age
      - Integer age 로 해결할 수 있다.

<br>
<br>
<br>

### 2-2. 기본값 적용

- @RequestParam(defaultValue = "-1") int age
- @RequestParam(defaultValue = "GUEST") String username
  - 빈문자인 경우에도 default 적용된다.

<br>
<br>
<br>

### 2-3. 파라미터 Map으로 조회

- 파라미터를 Map으로 조회할 수 있다.
- 파라미터가 하나로 확실하지 않으면 MultiValueMap을 사용하자
- @RequestParam Map
  - Map(key = value)
- @RequestParam MultiValueMap
  - MultiValueMap(key=[value1, value2, ...])

```java
@ResponseBody
@RequestMapping("/request-param-map")
public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
    log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
    return "ok";
}
```

<br>
<br>
<br>
<br>

## 3. HTTP 요청 파라미터 - @ModelAttribute

- @ModelAttribute
  - 요청 파라미터를 받아서 필요한 객체를 만들고 값을 넣어주는 과정을 자동화 해준다.

```java
@Data
public class TempData {
    private String username;
    private int age;
}

@Controller
class MemberController {
    ...
    
    @ResponseBody
    @RequestMapping("/model-attribute")
    public String modelAttribute(@ModelAttribute TempData tempData) {
        log.info("username={}, age={}", tempData.getUsername(), tempData.getAge());
        return "ok";
    }
}
```

<br>
<br>
<br>

### 3-1. 요청 파라미터를 바인딩 받을 객체를 만들어서 사용하자.

- 스프링 MVC는 @ModelAttribute가 있으면 다음과 같이 실행된다.
  - TempData 객체 생성
  - 요청 파라미터의 이름으로 TempData의 프로퍼티를 찾아서 setter를 호출하여 바인딩한다.

<br>
<br>
<br>

### 3-2. 프로퍼티

- getUsername(), setUsername() 메서드가 있다면
  - 객체는 username이라느 프로퍼티를 가지고 있다.
  - username 프로퍼티의 값을 변경하면 setUsername() 호출,
  - username 프로퍼티의 값을 조회하면 getUsername() 호출

<br>
<br>
<br>

### 3-3. @ModelAttribute는 생략할 수 있다.

- @RequestParam 
  - String, int 같은 단순 타입
- @ModelAttribute
  - argument resolver로 지정해둔 타입 외

<br>
<br>
<br>
<br>

## 4. HTTP 요청 메시지 - 단순 텍스트

- HTTP message body에 데이터를 직접 담아서 요청한다.
  - HTTP API에서 주로 사용
    - JSON, XML, TEXT
  - 데이터 형식
    - 주로 JSON
  - POST, PUT, PATCH

<br>

- 요청 파라미터와 다르다.
  - HTTP 메세지 바디를 통해 데이터가 직접 넘어온다.
  - @RequestParam, @ModelAttribute를 사용할 수 없다.
  - HTTP Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.

<br>
<br>
<br>

### 4-1. HTTP 메세지 바디 - 텍스트 메시지 (InputStream, HttpEntity)

- inputStream을 사용한다.

```java
@PostMapping("/request-body-string")
public void requestBodyString(HttpServletRequest request, 
        HttpServletResponse response) throws IOException {
    
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    
    log.info("messageBody={}", messageBody);
        
    response.getWriter().write("ok");
}
```

<br>
<br>
<br>

- 파라미터를 InputStream, OutputStream을 사용 할 수 있다.
- InputStream(Reader)
  - HTTP 요청 메시지 바디의 내용을 직접 조회
- OutputStream(Writer)
  - HTTP 응답 메시지의 바디에 직접 결과 출력

```java
@PostMapping("/request-body-string")
public void requestBodyString(InputStream inputStream, 
        Writer responseWriter) throws IOException {
    
    String messageBody  = StreamUtils.copyToString(inputStream, StandardCharset.UTF_8);
    log.info("messageBody={}", messageBody);
    
    responseWriter.write("ok");
} 
```

<br>
<br>
<br>

### 4-2. HttpEntity

- HTTP header, body 정보를 편리하게 조회
- 메시지 바디 정보를 직접 조회
- 요청 파라미터를 조회하는 기능과 관계 없다. (@RequestParam X, @ModelAttribute X)
- HttpEntity 응답에 사용 가능
  - 메시지 바디 정보 직접 반환
  - 헤더 정보 포함 가능
  - view 조회 X

<br>

- HttpEntity 상속 받을 시 다음 객체들도 같은 기능 제공
- RequestEntity
  - HttpMethod, url 정보가 추가, 요청에서 사용
- ResponseEntity
  - HTTP 상태 코드 설정 가능, 응답에서 사용
  - return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.OK);

```java
@PostMapping("/request-body-string")
public HttpEntity<String> requestBodyString(HttpEntity<String> httpEntity) {
    String messageBody = httpEntity.getBody();
    log.info("messageBody={}", messageBody);
    
    return new HttpEntity<>("ok");
}
```

<br>
<br>
<br>

### 4-3. @RequestBody, @ResponseBody

- @RequestBody를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다.
  - 헤더 정보가 필요하면 HttpEntity, @RequestHeader를 사용하자.
- @ResponseBody를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.

<br>
<br>
<br>
<br>

## 5. HTTP 요청 메시지 - JSON

- 기존에는 
  - HttpServletRequest를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서(InputStream) 문자로 변환(StreamUtils)한다. 
  - Jackson 라이브러리인 ObjectMapper를 사용해서 자바 객체로 변환한다. 

<br>
<br>
<br>

### 5-1. @RequestBody 문자 변환