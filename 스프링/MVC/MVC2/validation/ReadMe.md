컨트롤러의 중요한 역할로 HTTP 요청이 정상인지 검증이 있습니다.

<br>
<br>

# 1. 클라이언트 검증 & 서버 검증

클라이언트에서 검증할까 서버에서 검증할까 ?

<br>

사실 둘 다 해야합니다.

<br>

- 클라이언트 검증 
  - 조작할 수 있으므로 보안에 취약합니다.
- 서버 검증
  - 즉각적인 고객 사용성이 부족합니다.
  - 서버에서만 오류를 알 수 있기 때문입니다.
- 서버, 클라이언트 검증을 섞어서 사용해야 합니다.
- 최종적으로는 서버 검증이 필수입니다.
- API 방식을 사용하면 API 스펙을 잘 정의해서 검증 오류를 API 응답 결과에 남겨주어야 합니다.

<br>
<br>
<br>

# 2. 타임리프, 서버

## 2-1. 검증 오류 보관

- `Map<String, String> errors = new HashMap<>();`
  - 검증 시 오류가 발생하면 어떤 검증에서 오류가 발생했는지 정보를 담아둡니다.
  - 필드명을 key로 사용해서 어떤 필드에서 오류가 발생했는지 구분합니다.

```java
Map<String, String> errors = new HashMap<>();

if (!StringUtils.hasText(item.getItemName())) {
    errors.put("itemName", "상품 이름은 필수 입니다.");   
}
```

<br>
<br>
<br>

## 2-2. 검증 실패시 다시 입력 폼으로 

- 검증에서 오류 메시지가 있다면 model에 errors를 담고 입력 폼이 있는 뷰 템플릿으로 보냅니다.

```java
if (!errors.isEmpty()) {
    model.addAttribute("errors", errors);
    return "validation/v1/addForm";
}
```

```html
<html>
...

<form action="item.html" th:action th:object="${item}" method="post">
  <div th:if="${errors?.containsKey('globalError')}">
    <p class="field-error" th:text="${errors['globalError']}">전체 오류 메시지</p>
  </div>
  
  <div...
    
  </div>
</form>

...
</html>
```

<br>
<br>
<br>
<br>


# 3. 정리

- 만약 검증 오류가 발생하면 입력 폼을 다시 보여줍니다.
- 검증 오류들을 고객에게 친절하게 안내해서 다시 입력할 수 있게 합니다
- 검증 오류가 발생해도 고객이 입력한 데이터가 유지됩니다.

<br>
<br>
<br>
<br>

# 4. 버전 1의 남은 문제점

- 뷰 템플릿 내 중복
- 타입 오류 처리 X
  - 스프링 MVC에서 컨트롤러에 진입하기 전에 예외 발생
    - 컨트롤러가 호출되지 않습니다.
    - 400 예외가 발생하면서 오류 페이지를 띄워 줍니다.
- price에 문자를 입력하는 것 같은 타입 오류는 int나 Integer 둘 다 보관할 수 없습니다.
  - 문자 바인딩이 불가능합니다
  - 고객의 입력 문자가 사라집니다.
  - 고객은 오류 원인을 알지 못합니다.


<br>

결과적으로 고객이 입력한 값도 어딘가에서 관리해야 합니다.

이것은 버전 2에서 다루도록 하겠습니다.