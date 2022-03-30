


# 예외 처리 방식

## 오류 코드 리턴 X, 예외를 throw 하라 !

> 예외를 던지는 것이 명확하고 처리 흐름이 깔끔해진다.

<br><br>

## 예외를 던지고, 처리까지 🤔 (Checked Exception)

1. 오류가 발생한 부분에서 예외를 던진다.
2. checked exception에 대한 예외처리를 하지 않는다면 메소드 선언부에 throw를 명시 !
3. 예외를 처리할 수 있는 곳에서 catch하여 처리한다.

<br>

- checked Exception은 반드시 예외를 처리해줘야 할 때 사용한다.
    - 에러를 받은 부분에서는 catch를 해서 에러를 처리해주거나 
    - exceptiond을 상위에서 처리하겠다는 의미로 throws를 메서드 시그니처에 붙여줘야 한다.


정상적으로 밥을 줄 수 없으면 NotEnoughFoodError을 가져오게 된다.

오류가 발생한 부분에서 예외를 던지게 된다.




```java

public class AnimalController {
// ...
    public void giveFood() {
        try {
            tryToGiveFood();
        } catch (NotEnoughFoodError e) {
            logger.log(e);
        }
    }

    private void tryToGiveFood() throws NotEnoughFoodError {
        AnimalHandle handle = getHandle(ANIMAL1);
        // ...
    }

    private AnimalHandle getHandle(AnimalID id) {
        // ...
        throw new NotEnoughFoodError("Not Enough Food For :" + id.toString());
        // ...
    }

}

```

<br><br><br>

# Unchecked Exception을 사용하자 😋

> 왜 Checked Exception이 아닌 Unchecked Exception을 사용하라고 할까? 🤔
>
> C#, Python 등에서 Checked Exception이 없는데도 프로그램이 안정적이다.
>
> Checked Exception 사용 시에 명시적인 에러에 대한 처리가 필요한데 우리가 해줄 수 있는게 거의 없다고 볼 수 있다.
>
> 때문에 명시적인 예외처리가 필요하지 않은 Unchekd Exception을 사용하는게 좋다. 💁‍♂️
>
> 하지만 !! 특수한 경우에 Checked Exception을 사용하는게 더 나을 수도 있다.

<br><br><br>

## Exception 가계도

> 우리는 RuntimeException을 상속받아서 사용하자.

- *Exception*을 상속하면 Checked Exception 명시적인 예외처리가 필요하다.
    - 예시) IOException, SQLException

- *RuntimeException*을 상속하면 UncheckedExcepton 명시적인 예외처리가 필요하지 않다.
    - 예시) NullPointException, IllegalArgumentException, IndexOutOfBoundException

<br><br><br>

## Checked Exception이 나쁜 이유

- 특정 메소드에서 checked exception을 throw하고 
    - 상위 메소드에서 그 exception catch하면

    - 모든 중간 단계 메소드에 exception을 throws 해야한다.

- OCP (개방 폐쇄 원칙) 위배
    - 상위 레벨 메소드에서 하위 레벨 메소드의 디테일에 대해 알아야 하기 때문에 OCP 원칙에 위배



밑에 코드는 위서 봤던 AnimalController이다. 한번 다시 봐보자 👍
    
```java

    public class AnimalController {
    // ...
        public void giveFood() {
            try {
                tryToGiveFood();
            } catch (NotEnoughFoodError e) {
                logger.log(e);
            }
        }

        private void tryToGiveFood() throws NotEnoughFoodError {
            AnimalHandle handle = getHandle(ANIMAL1);
            // ...
        }

        private AnimalHandle getHandle(AnimalID id) {
            // ...
            throw new NotEnoughFoodError("Not Enough Food For :" + id.toString());
            // ...
        }

    }

```

<br><br><br>

# Exception 어떻게 작성할까?

## 예외에 의미있는 정보 담기

<br>

- 오류가 발생한 원인과 위치 찾기 쉽도록, 전후 상황을 붙인다.

- 실패한 연산 이름과 유형 등 정보를 담아 throw하자

<br>

## exception wrapper (예외를 감싸는 클래스 만들기)

> 예외를 감싸는 클래스 만들자
>
> 🤔 왜 예외를 감싸는 클래스를 만들라고 할까?
>
> 같은 행위를 하는 경우일 때  !! wrapping 해주자
>
> 호출부에서 발생한 하나의 Exception에 대해서만 처리 해준다 ! 💁‍♂️ (호출부에서 더 깔끔하게 호출, 처리 할 수 있게 된다.)

<br>

- `port.open()` 시 발생하는 checked Exception들을 감싸도록 port를 가지는 LocalPort 클래스를 만든다.

```java

LocalPort port = new LocalPort(12);

try {
    port.open();
} catch (PortDeviceFailure e) {
    reportError(e);
    logger.log(e.getMessage(), e);
} finally {
    // ...
}

public class LocalPort {
    private ACMEPort innerPort;
    public LocalPort(int portNumber) {
        innerPort = new ACMEPort(portNumber);
    }

    public void open() {
        try {
            innerPort.open();
        } catch (DeviceResponseException e) {
            throw new PortDeviceFailure(e);
        } catch (ATM1212UnlockedException e) {
            throw new PortDeviceFailure(e);
        } catch (GMXError e) {
            throw new PortDeviceFailure(e);
        }
    }
}

```

<br><br><br>

# 실무 예외 처리 패턴 😋

## getOrElse - 예외 대신 기본 값 리턴

<br><br><br>

## 1. null이 아닌 기본 값을 리턴한다.

<br>

### **나쁜 코드 예시 😡** (null을 반환할 떄)

> getEmployees를 설계할 때, 데이터가 없는 경우를 null로 표현했다.
>
> null을 리턴한다면 이후 코드에서 모두 null 체크가 있어야한다.

```java
// 나쁜 코드 예시 😡

    List<Employee> employees = getEmployees();
    if (employee != null) {
        for (Employee e : employees) {
            totalPay += e.getPay();
        }
    }

```

<br>

### **좋은 코드 예시 😆** (빈 컬렉션, 빈 문자열을 반환할 때)

> 복수형의 데이터를 가져올 때는 데이터의 없음을 의미하는 컬렉션을 리턴하면 된다.
>
> null보다 size가 0인 컬렉션이 훨씬 안전하다. (`Collections.emptyList())`

```java
// 좋은 코드 예시 😆

    List<Employee> employees = getEmployees();
    for(Employee e : employees) {
        totalPay += e.getPay();
    }

    public List<Employee> getEmployees() {
        if (.. there are no employees ...) {
            return Collections.emptyList();
        }
    }

```

<br><br><br>

## 2. 도메인에 맞는 기본값을 가져온다.

### `빈 컬렉션`, `빈 문자열` 적용 불가능 할 때 해결방법

> 🤔 근데 위에서 `빈 컬렉션`, `빈 문자열`을 적용할 수 없는 경우에는 어떻게 해결해야 할까? 
> 
> 💁‍♂️ *도메인에 맞는 기본값을 가져온다*
> 
> 예외 처리를 데이터를 제공하는 쪽에서 처리해 호출부 코드가 심플해짐
>
> 코드를 읽어가며 논리적인 흐름이 끊기지 않는다.
> 
> 도메인에 맞는 기본값을 도메인 서비스에서 관리한다.

```java

public class UserService {
    private static final UserLevel USER_BASIC_LEVEL = UserLevel.BASIC;

    public UserLevel getUserLevelOrDefauly(Long userId) {
        try {
            User user = userRepository.findByUserId(userId);
            return user.getUserlevel();
        } catch (UserNotFoundException e) {
            return USER_BASIC_LEVEL;
        }
    }
}

```

<br><br><br>

## getOrElseThrow - null 대신 예외를 던진다.

<br>

> 💁‍♂️ null 체크에서 벗어나자
>
> 데이터를 제공하는 쪽에서 null 체크, 데이터 없을 시 예외처리
>
> 호출부에서 매번 null 체크 필요 X, 안전하며 가독성이 좋아졌다

```java

public class UserService {
    private static final UserLevel USER_BASIC_LEVEL = UserLevel.BASIC;

    public User getUserOrElseThrow(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("User is not found. userId = " + userId)
        }
        return user;
    }
}

```

## 파라미터 null 점검

> 💁‍♂️ null을 파라미터로 받지 못하게 하자

```java

public class DogFightCalculator {
    public double dogFightPercentage(Dog d1, Dog d2) {
        if (d1 == null || d2 == null) {
            throw InvalidArgumentException("Invalid argument for DogFightCalculator dogFightPercentage);
        }
        return (d1.fightPoint - d2.fightPoint);
    }
}

```

## 자신의 예외를 정의하자

> 💁‍♂️ 자신의 예외를 정의하면 장점이 있다.
>
> 에러 로그에서 stacktrace 할 때 우리가 발생시킨 예외인 것을 인지할 수 있다.
>
> 다른 라이브러리에서 발생하는 에러와 섞이지 ㅇ낳는다.
>
> 어느 부분에서 에러가 났는지 파악하기 용이하다.
>
> 우리 시스템에서 발생한 에러 종류를 나열할 수 있다.


```java

public class MyDogException extends RuntimeException {
    private MyErrorCode errorCode;
    private String errorMessage;

    public MyDogException(MyErrorCode errorCode) {
        // ...
    }

    public MyDogException(MyErrorCode errorCode, String errorMessage) {
        // ...
    }
}

public enum MyErrorCode {
    private String defaultErrorMessage;

    INVALID_REQUEST("잘못된 요청");
    // ...
}

// 호출부
if (request.getDogName() == null) {
    throw new MyDogException(ErrorCode.INVALID_REQUEST, "dogName is null");
}

```