
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