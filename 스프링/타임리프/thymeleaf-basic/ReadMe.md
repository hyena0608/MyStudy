
## 타임리프 - 기본기능 🚀

타임리프 기본 기능에 대하여 배웁니다.

1. 타임리프 - 기본 기능 - 텍스트 - text, utext 
1. 타임리프 - 기본 기능 - 변수 - SpringEL
1. 타임리프 - 기본 기능 - 기본 객체들
1. 타임리프 - 기본 기능 - 유틸리티 객체와 날짜 
1. 타임리프 - 기본 기능 - URL 링크
1. 타임리프 - 기본 기능 - 리터럴
1. 타임리프 - 기본 기능 - 연산
1. 타임리프 - 기본 기능 - 속성 값 설정
1. 타임리프 - 기본 기능 - 반복

<br>
<br>

설명은 각각 Controller와 html 두 파트로 나눠져 있다.

### 1. 텍스트 - text, utext

BasicController
```java
@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello Spring!");
        return "basic/text-basic";
    }
}
```
html
```html
<li>th:text 사용 <span th:text="${data}"></span></li>
<li>컨텐츠 안에서 직접 출력 = [[${data}]]</li>
```

<br>
<br>

### 2. SpringEL

```java
// Controller

model.addAttribute("user", userA);
model.addAttribute("userList", list);
model.addAttrivute("userMap", map);
```

***변수 표현식*** : `${...}`

1. **Object**
    
    ```java
    // html
    // user.getUsername()과 같음

    ${user.username}
    ${user['username']}
    ${user.getUsername()}
    ```

2. **List**

    ```java
    // html
    // user.getUsername()과 같음

    users[0].username
    `list.get(0).getUsername()
    user[0]['username']
    ```

3.  **Map**

    ```java
    // html
    // user.getUsername()과 같음

    userMap['userA'].username
    map.get("userA").getUsername()
    userMap['userA']['username']
    userMap['userA].getUsername()
    ```

4. **지역 변수 선언**

    **`th:with`**

    를 사용해서 지역변수를 선언할 수 있다.

    아래는 그에 대한 예시이다.

    ```html
    <div th:with="first=${user[0]}">
        <p><span th:text="${first.username}"></span></p>
    </div>
    ```

    <br>
    <br>

### 3. 기본 객체

- ${#request}
- ${#response}
- ${#session}
- ${#servletContext}
- ${#locale}
- ${param.paramData}
- ${session.sessionData}
- ${@helloBean.hellp('Spring')}

<br>
<br>

### 4. 유틸리티 객체와 날짜

타임리프는 문자, 숫자, 날짜 , URI 등의 유틸리티 제공

아래 링크로 대체

- https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expression-utility- objects


<br>
<br>

### 5. URL 링크

Controller.java
```java
@GetMapping("/link")
public void link(Model model) {
    model.addAttribute("param1", "data1");
    model.addAttribute("param2", "data2");
}
```
link.html
```html
<!-- 쿼리 파라미터  /hello?param1=data1 -->
@{/hello(param1=${param1})} 

<!-- 경로변수  /hello/data1/data2 -->
@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}
```

<br>
<br>

### 6. 리터럴

***타임리프에서는 문자 리터럴을 항상 `'`로 감싸야 한다.***

아래와 같이 작은 따옴표로 감싸야 정상 작동한다.

<br>

html
```html
<span th:text="'hello world'"></span>
```

<br>

***리터럴 대체 `|    |`***

타임리프를 사용할 때 주로 리터럴 대체 문법을 사용한다.

아래와 같이 사용하면 된다.

<html>

```html
<span th:text="|hello + ${data}|"></span>
```

<br>
<br>

### 7. 연산

1. ***비교 연산***

html

```html
<!-- 1 > 10 -->
<span th:text="1 &gt; 10"></span>
<!-- 1 < 10> -->
<span th:text="1 &lt; 10"></span>
<!-- 1 >= 10 -->
<span th:text="1 &ge; 10"></span>
<!-- 1 <= 10> -->
<span th:text="1 &le; 10"></span>
<!-- 1 ! 10 -->
<span th:text="1 &not; 10"></span>
<!-- 1 == 10 -->
<span th:text="1 &eq; 10"></span>
<!-- 1 != 10 -->
<span th:text="1 &neq; 10"></span>
```

1. ***조건식***

html

```html
<!-- (10 % 2 == 0) ? '짝수' : '홀수' -->
<span th:text="(10 % 2 == 0)?'짝수':'홀수'"></span>
```

1. ***Elvis***

    조건식의 편의 버전

    <br>

    html
    
    ```html

    ```

1. ***No-Operation***

<br>
<br>

### 8.



<br>
<br>

### 9.