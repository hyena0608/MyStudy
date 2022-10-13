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


## 2-3. 정리

- 만약 검증 오류가 발생하면 입력 폼을 다시 보여줍니다.
- 검증 오류들을 고객에게 친절하게 안내해서 다시 입력할 수 있게 합니다
- 검증 오류가 발생해도 고객이 입력한 데이터가 유지됩니다.

<br>
<br>
<br>
<br>

## 2-4. 버전 1의 남은 문제점

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

<br>
<br>
<br>
<br>

# 3. BindingResult - 필드, 글로벌

- 스프링이 제공하는 검증 오류 처리 방법
- BindingResult 파라미터의 위치는 @ModelAttribute 다음에 와야한다.

<br>
<br>

## 3-1. 필드 오류 - FieldError

- 필드에 오류가 있을 때 FieldError 객체를 생성해서 bindingResult에 담아둔다.
- objectName : @ModelAttribute 이름
- field : 오류가 발생한 필드 이름
- defaultMessage : 오류 기본 메시지

<br>
<br>

### 3-1-1. FieldError 생성자 요약

```java
public FieldError(String objectNamt, String field, String defaultMessage) {}
```

<br>
<br>
<br>

### 3-1-2. FieldError 사용

```java
if (!StringUtils.hasTest(item.getItemName())) {
    bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
}
```

<br>
<br>
<br>

## 3-2. 글로벌 오류 - ObjectError

- 특정 필드를 넘어서는 오류가 있으면 ObjectError 객체를 생서애서 bindingResult에 담아둔다. 
- objectName : @ModelAttribute의 이름
- defaultMessage : 오류 기본 메시지

<br>
<br>

- ObjectError 생성자 요약

```java
public ObjectError(String objectName, String defaultMessage) {}
```

<br>
<br>

- ObjectError 사용

```java
bindingResult.addError(new ObjectError("item", "가격 * 수향의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
```

<br>
<br>
<br>

## 3-3. 타임리프 스프링 검증 오류 통합 기능

타임리프는 스프링의 BindingResult를 활용해서 검증 오류를 표현하는 기능 제공

- `#fields` : #fields로 BindingResult가 제공하는 검증 오류에 저븍ㄴ
- `th:errors` : 해당 필드에 오류가 있는 경우에 태그 출력
  - `th:if`의 편의 버전
- `th:errorclass` : th:field에서 지정한 필드에 오류가 있으면 class정보를 추가

<br>
<br>

### 3-3-1. 글로벌 오류 처리

```java
<div th:if="${#fields.hasGlobalErrors()}">
    <p class="field-class" 
        th:each="err : ${#fields.globalErrors()}"
        th:text="${err}">
        전체 메시지 오류
    </p>
</div>
```

<br>
<br>
<br>

### 3-3-2. 필드 오류 처리

```java
<input type="text" id="itemName" 
        th:field="*{itemName}"
        th:errorclass="field-error"
        class="form-control"
        placeholder="이름을 입력하세요">
    <div class="field-error" th:errors="*{itemName}">
        상품명 오류
    </div>
```

<br>
<br>
<br>
<br>

# 4. BindingResult - 데이터 바인딩 시 오류

- 스프링이 제공하는 검증 오류를 보관하는 객체
- BindingResult가 있으면 @ModelAttribute에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다.

<br>
<br>


## 4-1. @ModelAttribute에 바인딩 시 타입 오류 발생

<br>
<br>

### 4-1-1.BindingResult가 없으면
  
- 400 Error
- 컨트롤러 호출 X
- 오류 페이지로 이동

<br>
<br>
<br>

### 4-1-2. BindingResult가 있으면

- 오류 정보 (FieldError)를 BindingResult에 담는다. 
- 컨트롤러 정상 호출

<br>
<br>
<br>

## 4-2. BindingResult와 Errors

- org.springframework.validation.Errors
- org.springframework.validation.BindingResult

<br>

- BindingResult는 인터페이스, Errors 인터페이스를 상속받고 있다.
- 구현체는 `BeanPropertyBindingResult`
  - 둘 다 구현하고 있으므로 BindingResult 대신 Errors를 사용할 수 있다.
- BindingResult는 추가적인 기능들을 제공한다.
- 주로 BindingResult를 사용한다.

<br>

`BindingResult`, `FieldError`, `ObjectError`를 사용해서 오류 메시지 처리 완료

하지만 오류 발생시 입력 내용 모두가 사라진다.

<br>
<br>
<br>
<br>

# 5. FieldError, ObjectError

<br>
<br>

## 5-1. FieldError 생성자

- FieldError는 오류 발생시 사용자 입력 값을 저장하는 기능을 제공한다.
- `rejectedValue`가 오류 발생시 사용자 입력값을 저장하는 필드

<br>

```java
public FieldError(String objectName, String field, String defaultMessage);

public FieldError(String objectName, 
                    String field, 
                    @Nullable Object rejectedValue, 
                    boolean bindingFailure, 
                    @Nullable String[] codes,
                    @Nullable Object[] arguments,
                    @Nullable String defaultMessage)
```

- `objectName` : 오류가 발생한 객체 이름
- `field` : 오류 필드
- `rejectedValue` : 사용자가 입력한 값
- `bindingFailure` : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
- `code` : 메시지 코드
- `arguments` : 메시지에서 사용하는 인자
- `defaultMessage` : 기본 오류 메시지

<br>
<br>
<br>

## 5-2. 타임리프의 사용자 입력 값 유지

- `th:field="*{price}"`
- 정상 상황
  - Model 객체의 값 사용
- 오류 발생
  - FieldError에서 보관한 값을 사용해서 값을 출력

<br>
<br>
<br>

## 5-3. 스프링의 바인딩 오류 처리

- 타입 오류로 바인딩에 실패하면 스프링은 FieldError를 생성하면서 사용자가 입력한 값을 넣어둔다.
- 해당 오류 BindingResult에 담아서 컨트롤러를 호출한다.
- 타입 오류 같은 바인딩 실패시에도 사용자 오류 메시지 정상 출력 가능

<br>
<br>
<br>
<br>

# 6. 오류 코드와 메시지

오류 메시지를 체계적으로 다뤄야 합니다.

<br>
<br>

## 6-1. FieldError 생성자

- FieldError, ObjectError 생성자는 `errorCode`, `arguments`를 제공합니다.
  - 오류 발생시 오류 코드로 메시지를 찾기 위해 사용합니다.

```java
public FieldError(String objectName, String field, String defaultMessage);

public FieldError(String objectName, 
                    String field, 
                    @Nullable Object rejectedValue, 
                    boolean bindingFailure, 
                    @Nullable String[] codes,
                    @Nullable Object[] arguments,
                    @Nullable String defaultMessage)
```

- `objectName` : 오류가 발생한 객체 이름
- `field` : 오류 필드
- `rejectedValue` : 사용자가 입력한 값
- `bindingFailure` : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
- `code` : 메시지 코드
- `arguments` : 메시지에서 사용하는 인자
- `defaultMessage` : 기본 오류 메시지

<br>
<br>
<br>

## 6-2. errors 메시지 파일 생성

- `errors.properties`라는 별도의 파일로 관리합니다.

<br>
<br>

### 6-2-1. 스프링 부트 메시지 설정 추가

- application.properties

```java
spring.messages.basename=messages,errors
```

### 6-2-2. errors.properties

```properties
#required.item.itemName=상품 이름은 필수입니다.
#range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
#max.item.quantity=수량은 최대 {0} 까지 허용합니다.
#totalPriceMin=가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}

#==ObjectError==
#Level1
totalPriceMin.item=상품의 가격 * 수량의 합은 {0}원 이상이어야 합니다. 현재 값 = {1}

#Level2 - 생략
totalPriceMin=전체 가격은 {0}원 이상이어야 합니다. 현재 값 = {1}


#==FieldError==
#Level1
required.item.itemName=상품 이름은 필수입니다.
range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
max.item.quantity=수량은 최대 {0} 까지 허용합니다.

#Level2 - 생략

#Level3
required.java.lang.String = 필수 문자입니다.
required.java.lang.Integer = 필수 숫자입니다.
min.java.lang.String = {0} 이상의 문자를 입력해주세요.
min.java.lang.Integer = {0} 이상의 숫자를 입력해주세요.
range.java.lang.String = {0} ~ {1} 까지의 문자를 입력해주세요.
range.java.lang.Integer = {0} ~ {1} 까지의 숫자를 입력해주세요.
max.java.lang.String = {0} 까지의 문자를 허용합니다.
max.java.lang.Integer = {0} 까지의 숫자를 허용합니다.

#Level4
required = 필수 값 입니다.
min= {0} 이상이어야 합니다.
range= {0} ~ {1} 범위를 허용합니다.
max= {0} 까지 허용합니다.

# 추가

typeMismatch.java.lang.Integer = 숫자를 입력해주세요.
typeMismatch = 타입 오류 입니다.
```

<br>
<br>
<br>

## 6-3. FieldError, ObjectError 사용해서 처리

- codes : required.item.itemName를 사용해서 메시지 코드를 지정
- 메시지 코드는 하나가 아니라 배열로 여러 값을 전달할 수 있다.
  - 순서대로 매칭해서 처음 매칭되는 메시지가 사용된다.
- arguments : Object[]{1000, 1000000}를 사용해서 코드의 {0}, {1}로 치환할 값을 전달한다.

```java
@PostMapping("/add")
public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

  // 검증 로직
  if (!StringUtils.hasText(item.getItemName())) {
      bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
  }
  if (item.getPrice() == null || 1000000 < item.getPrice() || item.getPrice() < 1000) {
      bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
  }
  if (item.getQuantity() == null || item.getQuantity() >= 9999) {
      bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
  }

  // 특정 필드가 아닌 복합 룰 검증
  if (item.getPrice() != null && item.getQuantity() != null) {
    int resultPrice = item.getPrice() * item.getQuantity();
    if (resultPrice < 10000) {
      bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
    }
  }

  // 검증에 싪패하면 다시 입력 폼으로
  if (bindingResult.hasErrors()) {
    log.info("errors = {}", bindingResult);
    return "validation/v2/addForm";
  }

  // 성공 로직
  Item savedItem = itemRepository.save(item);
  redirectAttributes.addAttribute("itemId", savedItem.getId());
  redirectAttributes.addAttribute("status", true);
  return "redirect:/validation/v2/items/{itemId}";
}
```

<br>

- 위와 같이 `FieldError`, `ObjectError`를 다루기는 힘들다.
- 사실 컨트롤러에서 `BindingResult`는 검증해야 할 객체인 `target`을 알고 있습니다.

<br>
<br>
<br>

## 6-4. rejectValue(), reject()


<br>

- BindingResult가 제공하는 rejectValue(), reject()를 사용하면 
- FieldError, ObjectError를 직접 생성하지 않고, 깔끔하게 검증 오류를 다룰 수 있습니다.

<br>
<br>
<br>

### 6-4-1. rejectValue()

```java
void rejectValue(@Nullable String field, String errorCode, @Nullable @Object[] errorArgs, @Nullable String defauleMessage);
```

- field : 오류 필드 명
- errorCode : 오류 코드
  - messageResolver를 위한 오류 코드
- errorArgs : 오류 메시지에서 {0}를 치환하기 위한 값
- defaultMessage : 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시

```java
@PostMapping("/add")
public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

  // 검증에 실패하면 다시 입력 폼으로
  if (bindingResult.hasErrors()) {
    log.info("errors = {}", bindingResult);
    return "validation/v2/addForm";
  }

  log.info("objectName={}", bindingResult.getObjectName());


   검증 로직
  if (!StringUtils.hasText(item.getItemName())) {
      bindingResult.rejectValue("itemName", "required");
  }
  if (item.getPrice() == null || 1000000 < item.getPrice() || item.getPrice() < 1000) {
      bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
  }
  if (item.getQuantity() == null || item.getQuantity() >= 9999) {
      bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
  }

  // 특정 필드가 아닌 복합 룰 검증
  if (item.getPrice() != null && item.getQuantity() != null) {
    int resultPrice = item.getPrice() * item.getQuantity();
    if (resultPrice < 10000) {
      bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
    }
  }

  // 성공 로직
  Item savedItem = itemRepository.save(item);
  redirectAttributes.addAttribute("itemId", savedItem.getId());
  redirectAttributes.addAttribute("status", true);
  return "redirect:/validation/v2/items/{itemId}";
}
```

<br>
<br>
<br>

## 6-5. 축약된 오류 코드

오류 코드를 만들 때

자세히 만들거나

`required.item.itemName`: 상품 이름은 필수 입니다.

단순하게 만들 수 있다.

`required` : 필수 값 입니다.

<br>

단순하게 만들면 범용성이 좋지만, 세밀한 메시지 작성이 어렵습니다.

반대로 자세하게 만들면 범용성이 안좋아집니다.

<br>

가장 좋은 방법은

범용성으로 사용하다가, 세밀하게 작성해야 하는 경우에는 세밀한 내용이 적용되록 메시지에 단계를 두는 방법입니다.

<br>
<br>

코드가 있으면 이 메시지를 높은 우선 순위로 사용합니다.

```properties
# Level1
required.item.itemName : 상품 이름은 필수 입니다.

# Level2
required : 필수 값입니다.
```

<br>
<br>
<br>

## 6-6. MessageCodesResolver

- 검증 오류 코드로 메시지 코드들을 생성합니다.
- `MessageCodesResolver` 인터페이스이고 `DefaultMessageCodesResolver` 는 기본 구현체
- 주로 ObjectError, FieldError를 사용한다.

<br>
<br>

### 6-6-1. 동작 방식

- rejectValue(), reject()
  - 내부에서 MessageCodesResolver를 사용합니다.
  - 여기에서 메시지 코드들을 생성합니다.
- FieldError, ObjectError
  - FieldError, ObjectError 의 생성자를 보면, 오류 코드를 하나가 아니라 여러 오류 코드를 가질 수 있습니다.
  - MessageCodesResolver를 통해서 생성된 순서대로 오류 코드를 보관합니다.

<br>
<br>
<br>

### 6-6-2. FieldError - `rejectValue("itemName", "required")`

다음 처럼 오류 코드를 자동으로 생성합니다.

```properties
required.item.itemName
required.itemName
required.java.lang.String
required
```

<br>
<br>
<br>

### 6-6-3. ObjectError - `reject("totalPriceMin")`

다음 처럼 오류 코드를 자동으로 생성합니다.

```properties
totalPriceMin.item
totalPriceMin
```

<br>
<br>
<br>

### 6-6-4. 오류메시지 출력

- 타임리프 화면 렌더링할 때
  - th:errors가 실행된다.
  - 이때 오류가 있다면 생성된 오류 메시지 코드를 순선대로 돌아가면서 찾는다.
  - 없으면 디폴트 메시지를 출력한다.

<br>
<br>
<br>
<br>

## 6-7. 오류 코드 관리 전략

- 구체적인 것 -> 덜 구체적인 것
- MessageCodesResolver는 required.item.itemName처럼 구체적인 것을 먼저 만든다.
- required처럼 덜 구체적인 것을 가장 나중에 만든다.

<br>

즉, 

크게 중요하지 않은 메시지는 범용성 있는 `required`

정말 중요한 메시지는 구체적으로 적어서 사용한다.

<br>
<br>
<br>
<br>

## 6-8. 스프링이 직접 만든 오류 메시지 처리 - `typeMismatch`

검증 오류 코드는 2가지로 나뉩니다.

- 개발자가 설정한 코드 -> rejectValue()를 직접 호출
- 스프링이 직접 검증 오류에 추가 (주로 타입 정보)

<br>
<br>

### 6-8-1. 타입 필드가 맞지 않을 때

- 스프링은 타입 오류가 발생하면 `typeMismatch`라는 오류 코드를 사용한다.
- 오류 코드가 MessageCodesResolver를 통하면서 메시지 코드가 생성됩니다.

```properties
Failed to convert property value of type java.lang.String to required type
java.lang.Integer for property price; nested exception is
java.lang.NumberFormatException: For input string: "A"
```

위와 같은 코드가 사용자에게 노출되면 안됩니다.

그러기 위해서는 다른 메시지가 나올 수 있도록 해줘야 합니다.

<br>
<br>

먼저 로그를 확인해보면

BindingResult에 FieldError가 담겨 있는데, 다음과 같은 메시지 코드를 볼 수 있습니다.

`codes[typeMismatch.item.price,typeMismatch.price,typeMismatch.java.lang.Integer,typeMismatch]`

<br>

풀어보면 다음과 같습니다.

```properties
typeMismatch.item.price
typeMismatch.price
typeMismatch.java.lang.Integer
typeMismatch
```

<br>
<br>

오류 코드에 대한 메시지를 다음과 같이 변경해주면 됩니다.

- errors.properties에 메시지 코드를 추가합니다.

```properties
typeMismatch.java.lang.Integer=숫자를 입력해주세요.
typeMismatch=타입 오류입니다.
```

<br>
<br>
<br>
<br>

# 7. Validator

- 복잡한 검증 로직을 분리해줍니다.

컨트롤러에서 검증 로직이 차지하는 부분이 큽니다.

이럴 때는 클래스로 역할을 분리해줘야 합니다.

분리한 검증 로직을 재사용할 수 있다는 이점도 있습니다.

<br>
<br>
<br>

## 7-1. 스프링 검증 인터페이스 - `Validator`

```java
public interface Validator {
    boolean supports(Class<?> clazz);
    void validate(Object target, Errors errors);
}
```

<br>

스프링은 검증을 체계적으로 제공하기 위해 위의 인터페이스를 제공해줍니다.

```java
@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;


//         검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || 1000000 < item.getPrice() || item.getPrice() < 1000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
```

<br>

- ItemValidator를 스프링 컨테이너에 올려서 사용합니다.
- 코드 검증 로직이 매우 깔끔해 집니다.

```java

@Controller
@RequestMapping("")
@RequiredArgsConstructor
class ItemController {

  private final ItemRepository itemRepository;
  private final ItemValidator itemValidator;
  
  @PostMapping("/add")
  public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    itemValidator.validate(item, bindingResult);

    // 검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "validation/v2/addForm";
    }

    // 성공 로직
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v2/items/{itemId}";
  }
}
```

<br>
<br>
<br>

## 7-2. WebDataBinder

- `Validator` 인터페이스를 별도로 제공
  - 체계적으로 검증 기능을 도입하기 위함
  - 검증기를 만들면 스프링의 추가적인 도움을 받을 수 있다.
- `WebDataBinder`
  - 스프링의 파라미터 바인딩의 역할
  - 검증 기능도 내부에 포함

<br>

- ItemController에 다음과 같은 코드를 추가해줍니다.

```java
...
class ValidationItemControllerV2 {
  private final ItemValidator itemValidator;

  @InitBinder
  public void init(WebDataBinder dataBinder) {
    dataBinder.addValidators(itemValidator);
  }
}
```

- `WebDataBinder`에 검증기를 추가하면 해당 컨트롤러에서는 검증기를 자동으로 적용할 수 있다.
- @InitBinder : 해당 컨트롤러에만 영향

<br>
<br>
<br>

### 7-2-1. @Validated

- `validator`를 직접 호출하는 부분이 사라진다.
- 검증 대상 앞에 `@Validated` 가 붙는다.

```java
...
private final ItemValidator itemValidator;

@InitBinder
public void init(WebDataBinder dataBinder) {
    dataBinder.addValidators(itemValidator);
}
        
@PostMapping("/add")
public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    // 검증에 실패하면 다시 입력 폼으로
    if (bindingResult.hasErrors()) {
        log.info("errors = {}", bindingResult);
        return "validation/v2/addForm";
    }

    // 성공 로직
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v2/items/{itemId}";
}
```

<br>
<br>
<br>

### 7-2-2. 동작 방식 - `@Validated`

- @Validated
  - 검증기를 실행하라는 어노테이션
  - WebDataBinder에 등록한 검증기를 찾아서 실행
  - `supports()` 사용
  - 여러 검증기를 등록한다면 그 중에 어떤 검증기가 실행되어야 할지 구분한다.

<br>
<br>
<br>

### 7-2-3. @Validated, @Valid

- `javax.validation.@Valid`
  - build.gradle 의존관계 추가 필요하다.
- `import org.springframework.validation.annotation.Validated`

<br>
<br>
<br>
<br>

# 8. Bean Validation

- 검증 기능 어노테이션으로 구현
- 아래와 같은 검증 로직을 모든 프로젝트에 적용할 수 있게 표쥰화 한 것이 Bean Validation이다.

```java
public class Item {
    private Long id;
    
    @NotBlank
    private String itemName;
    
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    
    @NotNull
    @Max(9999)
    private Integer quantity;
    
    // ...
}
```

<br>
<br>
<br>

## 8-1. Bean Validation 이란 ?

- Bean Validation
  - 검증 어노테이션과 여러 인터페이스의 모음
  - 일반적으로 사용하는 구현체는 하이버네이트 Validator

<br>
<br>
<br>

## 8-2. Bean Validation Without Spring

- 의존 관계 추가
- `implementation 'org.springframework.boot:spring-boot-starter-validation'`

```java
@Data
public class Item {
    private Long id;
    
    @NotBlank
    private String itemName;
    
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    
    @NotNull
    @Max(9999)
    private Integer quantity;
    
    public Item() {}
  
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
```

<br>
<br>

- `ValidatorFactory`
  - 검증기를 생성한다
- `ConstraintViolation`
  - 검증 결과를 받는다.
  - 출력 결과를 보면 검증 오류가 발생한 객체, 필드, 메시지 정보등 다양한 정보를 확인할 수 있다.

```java
@Test
void beanValidation() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    Item item = new Item();
    item.setItemName(" ");
    item.setPrice(0);
    item.setQuantity(10000);
        
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
}
```

<br>
<br>
<br>

## 8-3. Bean Validation With Spring

- Spring MVC가 Bean Validation 사용하는 법
  - `spring-boot-starter-validation` 라이브러리를 넣으면 자동으로 Bean Validator를 인지한다.
- 자동 글로벌 Validator
  - `LocalValidatorFactoryBean`을 글로벌 Validator로 등록한다.
  - Validator는 `@NotNull`같은 어노테이션을 보고 검증을 수행
  - 글로벌 Validator가 적용되어 있다.
    - `@Valid`, `@Validated`만 적용하면 된다.

<br>
<br>
<br>

### 8-3-1. 검증 순서

- `@ModelAttribute` 각각의 필드에 타입 변환 시도
  - 실패 시 `typeMismatch`로 `FieldError` 추가
- Validator 적용

<br>

- 바인딩에 성공한 필드 (타입 변환에 성공)
  - Bean Validation 적용

<br>
<br>
<br>
<br>

## 8-4. Bean Validation - 에러 코드

- Bean Validation이 기본 제공하는 오류 메시지를 좀 더 자세히 변경

<br>

- `bindingResult`를 보면
- 오류 코드가 어노테이션 이름을 등록된다.
  - `typeMismatch`와 유사하다.
- `NotBlank`라는 오류 코드를 기반으로 `MessageCodesResolver`를 통해 다양한 메시지 코드가 생성된다.

```java
@NotBlank
NotBlank.item.itemName
NotBlank.itemName
NotBlank.java.lang.String
NotBlank

@Range
Range.item.price
Range.price
Range.java.lang.Integer
Range
```

<br>

여기서 우리가 메시지를 또 등록 할 수 있다.

- `{0}` : 필드명
- `{1}, {2}`은 각 어노테이션 마다 다름

- errors.properties
```properties
# Bean Validation 추가
NotBlank={0} 공백 X
Range={0}, {2} ~ {1} 허용
Max={0}, 최대 {1}
```

<br>
<br>
<br>

# 9. Bean Validation 검증 오류

- `ObjectError`는 `@ScriptAssert()`를 사용하면된다.
- 단점
  - 제약이 많고 복잡하다.
  - 실무에서 검증 기능이 해당 객체의 범위를 넘어선다. -> 대응이 어렵다.
  - 오브젝트 오류(글로벌 오류)의 경우  `@ScriptAssert`을 사용하기 보다 직접 자바 코드로 작성하는 것이 권장된다.