- [경계](#경계)
  * [1. 경계란 ?](#1-경계란)
  * [2. 내 코드 보호하기](#2-내-코드-보호하기)
    + [😡 안 좋은 코드 예시 (보호되지 않음)](#😡-안-좋은-코드-예시-보호되지-않음)
    + [🤔 왜 보호되지 않은 안좋은 코드라고 하는 걸까?](#🤔-왜-보호되지-않은-안좋은-코드라고-하는-걸까)
    + [💁‍♂️  캡슐화란? (Encapsulation)](#💁‍♂️-캡슐화란-encapsulation)
    + [😜 좋은 코드 예시 (보호됨)](#😜-좋은-코드-예시-보호됨)
  * [3. 내부 코드와 외부 코드 호환 : 어댑터 패턴](#3-내부-코드와-외부-코드-호환--어댑터-패턴)
    + [어댑터 패턴이란? (Adapter Pattern)](#어댑터-패턴이란-adapter-pattern)
    + [어댑터 패턴 예시](#어댑터-패턴-예시)
  * [4. 외부 라이브러리 테스트 (Learning Test)](#4-외부-라이브러리-테스트-learning-test)
    + [🤔 라이브러리를 왜 테스트 할까?](#🤔-라이브러리를-왜-테스트-할까)

#  경계

<br>

## 1. 경계란 ? 

우리는 Open Source와 library를 항상 사용하는데 우리가 만든 코드와 외부 코드를 항상 섞어서 사용하고 있습니다.

외부에 있는 코드들과 내부에 있는 우리가 만든 코드의 스타일이 다르기 때문에 호환하는데 있어서 구분 지을 필요가 있습니다.

이렇게 경계를 구분 짓는데 여러 방법이 있습니다.

1. 내부 코드 보호 : 캡슐화
2. 내부 코드와 외부 코드 호환 : 어댑터 패턴 이용
3. 외부라이브러리 테스트 (Learning Test 이용하기)

가 있습니다. 순서대로 알아보겠습니다


<br><br><br>

## 2. 내 코드 보호하기

### 😡 안 좋은 코드 예시 (보호되지 않음)

- Map 인터페이스가 제공하는 불필요한 기능 노출
- 외부 코드가 함부로 호출하면 dog 데이터가 손상될 수 있음
    - 개발자의 의도에서 벗어나게 됨

```java

    Map<Dog> dogs = new HashMap<Dog>();
    Dog d = dogs.get(dogId);

    ---
    dogs.clear() // 의도되지 않은 기능 외부에 노출된다.

```

<br>

### 🤔 왜 보호되지 않은 안좋은 코드라고 하는 걸까?

- Dog Id와 Dog 객체로 저장 -> Map 사용
- Map 그대로 사용 -> `clear()`같은 의도되지 않은 함수가 호출돼 장애 발생
- Dog의 외부 코드 관점은 `객체들의 값만 가지고 싶다`
- 그렇다면 ? 💁‍♂️ 캡슐화를 하자 !

<br>

### 💁‍♂️  캡슐화란? (Encapsulation)

**객체의 실제 구현을 외부로부터 감추는 방식입니다**

- 밑에 코드를 보면 `private`으로 Map 데이터가 가려지고

- `public method`로 값을 호출 할 수 있게 `캡슐화`하였습니다.

<br>

### 😜 좋은 코드 예시 (보호됨)

- 캡슐화를 통해 Map 감추기
- 원하는 기능만 공개
- 적절한 경계 -> 코드 보호

```java

    public class Dogs {
        private Map<Dog> dogs = new HashMap<Dog>();

        public Dog getById(Long dogId) {
            return dogs.get(dogId);
        }
    }

```

<br><br><br>

## 3. 내부 코드와 외부 코드 호환 : 어댑터 패턴

<br>

### 어댑터 패턴이란? (Adapter Pattern)

💁‍♂️ 어댑터 패턴은 외부 코드를 호출할 때, 한 클래스의 인터페이스를 클라이언트에서 사용하고자 할 때, **우리가 정의한 인터페이스 대로 호출하기 위해 어댑터 클래스로 인터페이스를 통일 시켜 사용하는 패턴입니다.**

어댑터를 이용하면 인터페이스 호환성 문제 때문에 같이 쓸 수 없는 클래스들을 연결해서 사용할 수 있습니다.

### 어댑터 패턴 예시

Dog가 Cat을 Adapter를 이용하여 불러서 사용할 수 있다.

```java

    public interface Dog {
        public void run();
        public void bark();
    }

```

```java

public class MyDog implements Dog {
    
    public void run() {
        System.out.println("강아지가 달립니다.");
    }

    public void bark() {
        System.out.println("멍멍 !!");
    }
}

```

```java

    public interface Cat {
        public void walk();
        public void bark();
    }

```

```java

    public class MyCat implements Cat {

        public void walk() {
            System.out.println("고양이가 걷습니다.");
        }

        public void bark() {
            System.out.println("야옹 ~ ");
        }
    }

```

```java

    public class CatAdapter implements Dog {

        Cat cat;

        public CatAdapter(Cat cat) {
            this.cat = cat;
        }

        public void run() {
            cat.walk();
        }

        public void bark() {
            cat.bark();
        }
    }

```

```java

    public class App {

        public static void main(String[] args) {
            System.out.println("고양이");
            Cat cat = new Cat();
            cat.walk();
            cat.bark();

            System.out.println("고양이 어댑터");
            Dog catAdapter = new CatAdapter(cat);
            catAdapter.run();
            catAdapter.bark();

            System.out.println("강아지");
            Dog dog = new Dog();
            dog.run();
            dog.bark();
        }
    }

```

```

    고양이
    고양이가 걷습니다.
    야옹 ~
    고양이 어댑터
    고양이가 걷습니다.
    야옹 ~
    강아지
    강아지가 달립니다.
    멍멍 !!

```

<br><br><br>

## 4. 외부 라이브러리 테스트 (Learning Test)

### 🤔 라이브러리를 왜 테스트 할까?

**외부 코드를 배우고, 안정성 미리 검증**

- 학습 테스트는 이해도를 높여줍니다.
- 외부 코드의 버전 업데이트 했을 때 이전에 구현 했던 코드가 잘 동작하는지 확인할 수 있습니다.
    - 때문에 이슈 발생했을 때 좀 더 빠르게 대응할 수 있습니다.