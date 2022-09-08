
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

```java
@Controller
public class RequestBodyJsonController {
  
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @PostMapping("/request-body-json")
    public void requestBodyJson(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
          ServletInputStream inputStream = request.getInputStream();
          String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
      
          log.info("messageBody={}", messageBody);
          HelloData data = objectMapper.readValue(messageBody, HelloData.class);
          log.info("username={}, age={}", data.getUsername(), data.getAge());
          
          response.getWriter().write("ok");
    }
}
```

<br>
<br>
<br>

### 5-1. @RequestBody 문자 변환

- @RequestBody를 사용해서 HTTP 메세지에서 데이터를 꺼내고 messageBody에 저장한다.
- 문자로 된 JSON 데이터인 messageBody를 objectMapper를 통해서 자바 객체로 변환한다.

```java
@Controller
public class RequestBodyJsonController {
  
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @ResponseBody
    @PostMapping("/request-body-json")
    public String requestBodyJson(@RequestBody String messageBody) throws IOException {
          HelloData data = objectMapper.readValue(messageBody, HelloData.class);
          log.info("username={}, age={}", data.getUsername(), data.getAge());
          return "ok";
    }
}
```

<br>
<br>
<br>

### 5-2. @RequestBody 객체 파라미터

- @RequestBody 요청
  - JSON 요청 -> HTTP 메시지 컨버터 -> 객체
- @ResponseBody 응답
  - 객체 -> HTTP 메시지 컨버터 -> JSON 응답

<br>
<br>

- @RequestBody 객체 파라미터
  - @RequestBody에 직접 만든 객체를 지정할 수 있다.
  - HttpEntity, @RequestBody를 사용하면 HttpMessageConverter가 Http 메시지 바디의 내용을 원하는 문자나 객체로 변환해준다.
  - HttpMessageConverter는 문자 뿐만 아니라 JSON도 객체로 변환해준다.

<br>
<br>

- @RequestBody는 생략 불가능
  - 생략하면 @ModelAttribute가 적용된다.

<br>

```java
@ResponseBody
@PostMapping("/request-body-json")
public String requestBodyJson(@RequestBody HelloData data) {
    log.info("username={}, age={}", data.getUsername(), data.getAge());
    return "ok";
}
```

<br>

HttpEntity 사용해도 된다.

```java
@ResponseBody
@PostMapping("/request-body-json")
public String requestBodyJson(HttpEntity<HelloData> httpEntity) {
    HelloData data = httpEntity.get();
    log.info("username={}, age={}", data.getUsername(), data.getAge());
    return "ok";
}
```



<br>
<br>
<br>
<br>

## 6. HTTP 응답

- HTTP API 경우
  - HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.

<br>

- ResponseEntity
  - HTTP 응답 코드 설정 가능
  - 응답 코드 설정 시 @ResponseBody를 사용하면 설정하기 까다로움

<br>

- @RestController
  - 해당 컨트롤러에 @ResponseBody가 적용되는 효과

<br>
<br>
<br>
<br>

## 7. HTTP 메시지 컨버터

<br>

### 7-1. @ResponseBody 사용 원리

- @ResponseBody를 사용
  - HTTP의 BODY에 문자 내용을 직접 반환
  - viewResolver 대신에 HttpMessageConverter가 동작
  - 기본 문자처리 : StringHttpMessageConverter
  - 기본 객체처리 : MappingJackson2HttpMessageConverter
  - byte처리 등등 기타 여러 HttpMessageConverter가 기본으로 되어 있다.

<br>
<br>
<br>

### 7-2. HttpMessageConverter 선택하는법

- 응답의 경우 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보를 조합해서 선택한다.

<br>
<br>
<br>

### 7-3. 스프링 부트 기본 메시지 컨버터 

```
0 = ByteArrayHttpMessageConverter
1 = StringHttpMessageConverter
2 = MappingJackson2HttpMessageConverter
```

<br>

스프링 부트는 대상 클래스 타입과 미디어 타입을 체크해서 사용 여부를 결정한다.

- ByteArrayHttpMessageConverter
  - byte[] 데이터를 처리한다.
  - 클래스 타입 : byte[]
  - 미디어 타입 : */*
  - 요청 ex) @RequestBody byte[] data
  - 응답 ex) @ResponseBody return byte[] 
  - 쓰기 미디어 타입 : application/octet-stream
- StringHttpMessageConverter
  - String 문자로 데이터를 처리한다.
  - 클래스 타입 : String
  - 미디어 타입 : */*
  - 요청 ex) @RequestBody String data
  - 응답 ex) @ResponseBody return "ok" 
  - 쓰기 미디어 타입 : text/plain
- MappingJackson2HttpMessageConverter
  - 클래스 타입 : 객체 또는 HashMap
  - 미디어 타입 : application/json
  - 요청 ex) @RequestBody HelloData data
  - 응답 ex) @ResponseBody return helloData
  - 쓰기 미디어 타입 : application/json

<br>
<br>
<br>

### 7-4. HTTP 요청 데이터 읽기

- HTTP 요청이 오고, 컨트롤러에서 @RequestBody, HttpEntity 파라미터 사용
- 메시지 컨버터 canRead() 호출
  - 대상 클래스 타입 지원 ?
    - @RequestBody의 대상 클래스 (byte[], String, HelloData)
  - HTTP 요청 Content-Type 미디어 타입 지원 ?
    - text/plain, application/json, */*
- canRead() 조건을 만족하면 read()를 호출해서 객체 생성하고, 반환한다.

<br>
<br>
<br>

### 7-5. HTTP 응답 데이터 생성

- 컨트롤러에서 @ResponseBody, HttpEntity로 값이 반환된다.
- 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 canWrite()를 호출한다.
  - 대상 클래스 타입 지원 ?
    - return의 대상 클래스 (byte[], String, HelloData)
  - HTTP 요청의 Accept 미디어 타입을 지원하는가 ?
    - @RequestMapping의 produces를 의미한다.
    - text/plain, application/json, */*
- canWrite() 조건을 만족하면 write()를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성

<br>
<br>
<br>
<br>

## 8. 요청 매핑 핸들러 어뎁터 구조

- @RequestMapping을 처리하는 핸들러 어댑터인 RequestMappingHandlerAdapter에 있다.

<br>
<br>

### 8-1. RequestMappingHandlerAdapter 동작 방식

- DispatcherServlet -> 핸들러 어댑터 -> ArgumentResolver -> 핸들러(컨트롤러)
- DispatcherServlet -> 핸들러 어댑터 -> ReturnValueHandler -> 핸들러(컨트롤러)

<br>
<br>
<br>

### 8-2. ArgumentResolver

- HttpServletRequest
- Model
- @RequestParam
- @ModelAttribute
- @RequestBody
- HttpEntity

이와 같은 HTTP 메세지를 처리하는 부분까지 ArgumentResolver 유연하게 처리해줬다.

<br>

애노테이션 기반 컨트롤러를 처리하는 RequestMappingHandlerAdapter는 ArgumentResolver를 호출해서 컨트롤러가 필요로 하는 다양한 파라미터의 값을 생성한다.

파라미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘긴다.

- 스프링은 30개가 넘는 ArgumentResolver를 기본으로 제공한다.

<br>
<br>
<br>

### 8-3. ArgumentResolver (HandlerMethodArgumentResolver)

<br>

```java
public interface HandlerMethodArgumentResolver {

  boolean supportsParameter(MethodParameter parameter);
  
  @Nullable
  Object resolveArgument(MethodParameter parameter, 
                         @Nullable ModelAndViewContainer mavContainer, 
                         NativeWebRequest webRequest, 
                         @Nullable WebDataBinderFactory binderFactory) throws Exception;
}
```

<br>

- 동작 방식
  - ArgumentResolver의 supportsParameter()를 호출해서 해당 파라미터를 지원하는지 체크한다.
  - 지원하면 resolveArgument() 호출해서 실제 객체 생성
  - 생성된 객체 컨트롤러 호출시 넘어간다.

<br>
<br>
<br>

### 8-4. ReturnValueHandler (HandlerMethodReturnValueJandler)

- ArgumentResolver와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
- 컨트롤러에서 String으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분
- 스프링은 10개가 넘는 ReturnValueHandler를 지원한다.
- ModelAndView, @ResponseBody, HttpEntity, String

<br>
<br>
<br>
<br>

### 8-5. HTTP 메시지 컨버터

- DispatcherServlet -> 핸들어 어탭터 -> ArgumentResolver -> HTTP 메시지 컨버터 -> ArgumentResolver -> 핸들러
- DispatcherServlet <- 핸들어 어탭터 <- ReturnValueHandler <- HTTP 메시지 컨버터 <- ReturnValueHandler <- 핸들러

<br>
<br>

- 요청의 경우
  - @RequestBody를 처리하는 ArgumentResolver가 있다.
  - HttpEntity를 처리하는 ArgumentResolver가 있다.
  - ArgumentResolver들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성한다.
- 응답의 경우
  - @ResponseBody와 HttpEntity를 처리하는 ReturnValueHandler가 있다.

<br>
<br>

- @RequestBody, @ResponseBody가 있으면
  - RequestResponseBodyMethodProcessor (ArgumentResolver)
- HttpEntity가 있으면 
  - HttpEntityMethodProcessor (ArgumentResolver)를 사용한다.
