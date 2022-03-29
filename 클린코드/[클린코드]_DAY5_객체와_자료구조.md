# 목차

- [자료구조 VS 객체](#자료구조-vs-객체)
  * [자료구조](#자료구조)
  * [객체 (Object)](#객체-object)
  * [자료구조 VS 객체 언제 사용할까?](#자료구조-vs-객체-언제-사용할까)
    + [자료구조 사용할 때](#자료구조-사용할-때)
    + [객체 사용할 때](#객체-사용할-때)
- [OOP 디미터의 법칙](#oop-디미터의-법칙-😋)
  * [디미터의 법칙이 뭔데?](#디미터의-법칙이-뭔데)
  * [객체 지향과 디미터의 법칙](#객체-지향과-디미터의-법칙)
  * [기차 충돌](#기차-충돌)
- [DTO (Data Transger Object) 는 자료구조](#dto-data-transger-object-는-자료구조)
  * [Beans란 ?](#beans란)
- [Active Record 란?](#active-record-란)
- [Data Mapper](#data-mapper)

<br/><br/><br/><br/>


# 자료구조 VS 객체

<br><br>

## 자료구조

<br/>

데이터 그 자체

자료를 공개한다.

<span style="color: lightgreen">변수 사이에 조회 함수와 설정 함수로 변수를 다룬다고 객체가 되지 않는다. (`getter`, `setter`)</span>

> 💁‍♂️ Animal이 `자료구조`로 쓰였을 때
>
> 아래는 비즈니스 로직도 없고 그냥 값을 가져오기만 한다.
> 
> 이런 경우에는 자료구조라고 한다.


```java 

// 자료구조
public interface animal {
    long getShoutCount();
    long getBiteCount();
}

public class Dog implements Animal {
    long shoutCount;
    long biteCount;

    public long getShoutCount() {
        return this.shoutCount;
    }

    public long getBiteCount() {
        return this.biteCount;
    }
}

```

<br><br>

자료구조 Shape 예시를 하나 더 들자면

> 절차적인 코드는 새로운 자료 구조를 추가하기 어렵다.
>
> 함수를 고쳐야 한다.
>
> 만약 삼각형을 넣는다고 하면 area 메소드에 else if로 삼각형에 관해서 함수를 고쳐써야 한다.

```java

public class Square {
    public Point topLeft;
    public double side;
}

public class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
}

public class Circle {
    public Point center;
    public double radius;
}

public class Geometry {
    public final double PI = 3.14592653;

    public double area(Object shape) throws NoSuchShapeException {
        if (shape instanceof Square) {
            Square s = (Square) shape;
            return s.side * s.side;
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Ractangle) shape;
            return r.height * r.width;
        } else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return PI * c.radius * c.radius;
        }
        throw new NoSuchShapeException();
    }
}

```

<br><br><br>

## 객체 (Object)

<br>

비즈니스 로직과 관련

자료를 숨기고 추상화한다.

자료를 다루는 함수만 공개한다.

<span style="color: lightgreen">추상 인터페이스를 제공해 사용자가 구현을 모른 채 자료의 핵심을 조작할 수 있다.</span>

> 💁‍♂️ Animal이 `객체`로 쓰였을 때
>
>  비즈니스 로직을 가짐
>
>  자신이 가진 값을 바로 주는 것이 아니라 로직을 돌려서 준다.

```java

// 객체
public interface Animal {
    long getPercentBiteWhileShout();
}

public Dog implements Animal {
    long shoutCount;
    long biteCount;

    public Dog(long shoutCount, long biteCount) {
        if (Shout <= 0) {
            throw new IllegalArgumentException("shoutCount must be greater than zero");
        }
        this.shoutCount = shoutCount;
        this.biteCount = biteCount;
    }

    public double getPercentBiteWhileShout() {
        return this.biteCount / this.shoutCount * 100;
    }
}

```

<br><br><br>

객체도 Shape 예시를 하나 더 들겠다 !

> 객체지향 코드는 새로운 클래스를 추가하기 쉽다.
>
> 하지만 함수를 추가해야 한다.
>
> 마찬가지로 삼각형을 추가하기 쉽지만 area를 또 오버라이딩 해야하는 번거로움이 있다.

```java

public class Square implements Shape {
    private Point topLeft;
    private double side;

    public double area() {
        return side * side;
    }
}

public class Rectangle implements Shape {
    private Point topLeft;
    private double height;
    private double width;

    public double area() {
        return height * width;
    }
}

public class Circle implements Shape {
    private Point center;
    private double radius;
    public final double PI = 3.14159265;

    public double area() {
        return PI * radius * radius;
    }
}

```

<br><br><br>

## 자료구조 VS 객체 언제 사용할까?

> <span style="color:lightgreen">상황에 맞는 선택을 해야 한다.</span>

### 자료구조 사용할 때

- 자료구조를 사용하는 절차적인 코드는 기본 자료구조를 변경하지 않으면서 새 함수를 추가하기 쉬운 장점이 있다.

- 절차적인 코드는 `새로운 자료 구조`를 추가하기 어렵다.
    - 그러려면 모든 함수를 고쳐야 하는 큰 단점이 있다 !

### 객체 사용할 때

- 객체지향 코드는 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다.

- 객체 지향 코드는 `새로운 함수`를 추가하기 어렵다.
    - 그러러면 모든 클래스를 고쳐야 한다 !!!


<br><br><br><br>

# [OOP] 디미터의 법칙 😋

객체 맛집이 되기 위해서는 꼭 알아둬야 하는 상식 중에 상식 !!

<br>

## 디미터의 법칙이 뭔데?

어떤 객체가 다른 객체들에게 의존을 많이 하다보니, 결합도가 높아졌다 !

이를 개선하고자 <span style="color: lightgreen">객체에게 자료를 숨기는 대신 함수를 공개하도록 하였다.</span>

🤔 *자료를 숨기고 함수를 공개하라고 ???*

다른 객체가 어떠한 자료를 갖고 있는지 몰라야 한다는 것이다 !

**이를 준수하면 캡슐화를 높혀 객체의 자율성과 응집도를 높일 수 있다 !**

<br><br><br>

## 객체 지향과 디미터의 법칙

> <span style="color: lightgreen;">객체가 어떤 메시지를 주고 받는가?</span>가 매우 중요하다.
>
> 객체가 어떤 데이터를 갖고 있는가를 생각하는 것은 옳지 않은 것이다.
>
> 즉, 다른 객체가 어떠한 자료를 갖고 있는 지 알 필요가 없다.
>
> *우리는 그저 어떤 함수를 갖고 있는지만 알면 된다 !*


<br><br><br>

> <span style="color: lightgreen">클래스C의 메서드는 다음과 같은 객체의 메서드만 호출해야 한다.</span>
>
> - 클래스 C
> - 자신이 생성한 객체
> - 자신의 인수로 넘어온 객체
> - C 인스턴스 변수에 저장된 객체

<br><br><br>

## 기차 충돌

> 디미터의 법칙에 어긋나는 상황 1

```java

// 객체 - 기차 충돌, 디미터 법칙 😡 위배
final String barkDoggyName = a.getHouse().getDog().getName();

// 자료구조 - OK
final String barkDoggtName = a.house.dog.name;

// 객체에 대한 해결책 아니다 !! 단지 getter 를 통해 가져올 뿐이지 맨 위 객체랑 같이 디미터 법칙을 위배한다.
a.getDogNameInHouse();
a.getHouse().getDogName();

// 이러한 문제를 풀려면 생각해봐야 한다.
// 이름을 어디서 끌고 온걸까? 왜 필요했을까?
// 객체는 자료를 숨기고 자료를 다루는 함수만 공개한다는 것을 항상 명시해두자 !

```


<br><br><br>

# DTO (Data Transger Object) 는 자료구조

> <span style="color: lightgreen;">다른 계층 간 데이터를 교환할 때 사용</span>

- 로직 없이 필드만 갖는다.
- 일반적으로 클래스 명이 Dto || DTO로 끝난다.
- getter/setter를 갖기도 한다.

<br>

## Beans란 ?

> <span style="color: lightgreen;">Java Beans : 데이터 표현이 목적인 자바 객체</span>

- 멤버 변수는 private 속성이다.

- getter와 setter를 갖는다.

<br><br><br>

# Active Record 란?

> Entity도 아니고.. 뭐하는 친구일까? (잘 사용하지 않는다.)
>
> <span style="color: lightgreen;">Database row를 객체에 매핑하는 패턴</span>
>
> 객체가 row를 갖고 database에 대한 접근도 한다.

```java

public class Emplyoee extends ActiveRecord {
    private String name;
    private String address;
}

Employee co = Employee.findByName("코견");

co.setName("코견견");
co.save();

```

- 비즈니스 로직 메소드를 추가해 겍체로 취급하는 건 좋지 않다.
- 비즈니스 로직을 담으면서 내부 자료를 숨기는 객체는 따로 생성
- 객체가 많아지면 복잡하니까
    - Entity에 가단한 메서드를 추가해 사용한다.

<br><br><br>

# Data Mapper

> Active Record와 비교할 수 있는 데이터 표현 방법 (Spring 프로젝트에서 주로 내가 사용하는 방식)
>
> row를 담는 객체와 database에 접근할 수 있는 객체가 분리돼있음

```java
@Entity
class Employee {

    @Id @GeneratedValue
    private Long Id;

    private String name;
    private String address;
}

class EmployeeRepository extends JpaRepository<Employee, Long> {

    // ...
}
```