# 개요

개발자는 항상 기능을 만든다.

결국 함수를 만드는 게 우리의 일인데 이러한 함수를 잘 작성하는 법을 알아보자.

# 1. SOLID 란?

## SOLID 원칙

> 객체지향의 설계 5원칙으로 
> 
> 항상 코드를 짜면서 이 **SOLID 원칙**을 생각해주면서 짜는 습관을 들여야한다.

- SRP 단일 책임 원칙
- OCP 개방-폐쇄 원칙
- LSP 리스코프 치환 원칙
- ISP 인터페이스 분리 원칙
- DIP 의존성 역전 원칙

---

## SRP 단일 책임 원칙

> 한 클래스는 하나의 책임만 가져야 한다.

클래스는 `하나의 기능`만 가지며, 어떤 변화에 의해 클래스를 변경해야 하는 이유는 오직 하나뿐이어야 한다.

SRP 책임이 분명해지기 때문에, 변경에 의한 연쇄작용에서 자유로워질 수 있다.

**가독성 향상과 유지보수가 용이해진다.**

```
한 클래스가 생성도 하고, 판별도 하고, 계산도 하는 등 여러가지 기능이 들어 있다면 의존성이 높아질 것이다.

이러한 의존성이 높아지면 유지보수도 좋지 않고 가독성이 떨어지는 등 좋은 코드가 되지 않는다.
```

---

## OCP 개방-폐쇄 원칙

> 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야한다.

변경을 위한 비용은 가능한 줄이고, `확장`을 위한 비용은 가능한 극대화 해야 한다.

요구사항의  변경이나 추가사항이 발생하더라도, 기존 구성요소에는 수정이 일어나지않고, 기존 구성 요소를 쉽게 `확장해서 재사용`한다.

**객체지향의 추상화와 다형성을 활용한다.**

```
a, b, c 기능을 가진 클래스를 확장하여 기능 d를 추가했다.

근데 실제로 사용한 기능이 a, c, d가 되었다 !! 😡

좋은 코드라면 기존 구성요소에는 수정 없이 기능 a, b, c, d 모두 잘 동작할 것이다.
```

---

## LSP 리스코프 치환 원칙

> 서브 타입은 언제나기반 타입으로 교체할 수 있어야 한다.

서브 타입은 기반 타입이 약속한 규약(접근제한자,예외 포함)을 지켜야 한다.

클래스 상속, 인터페이스 상송을 이용해 `확장성`을 획득한다.

다형성과 확장성을 극대화하기 위해 `인터페이스`를 사용하는 것이 더 좋다.

합성(composition)을 이용할 수도 있다.

```
부모 A는 스팸을 사오는 기능이 있다.
부모 A가 자식 a를 가지게 됐는데

자식 a에게 똑같은 기능을 수행하게 했더니
무려 런천미트를 사오는 사태가 일어난다. 🤔

부모랑 교체가 된다고 하여도 제대로 스팸을 사와야 한다.
```

---

## ISP 인터페이스 분리 원칙

> 자신이 사용하지 않는 인터페이스는 구현하지 말아야 한다.

가능한 최소한의 인터페이스만 구현한다.

만약 어떤 클래스를 이용하는 클라이언트가 여러 개고, 이들이 클래스의 특정 부분만 이용한다면, 여러 인터페이스로 분류하여 클라이언트가 필요한 기능만 전달한다.

SRP가 클래스의 단일 책임이라면, ISP는 인터페이스의 단일 책임

```
설명하자면

기능 a, b, c를 가지고 있는 인터페이스 A가 있고

x, y가 A를 상속 받았을 때

x, y는 기능이 a, b, c 등이 있을 것이다.

근데 !!!

x는 실제로 상속 받아 사용하는 기능 a만 사용하고
y는 실제로 상속 받아 사용하는 기능은 c밖에 없다.

이럴 때 필요 없는 기능도 x, y에 들어가게 되므로

기능 a를 가진 인터페이스 X
기능 c를 가진 인터페이스 Y 를 만들어

x implements X
y implements Y 로 써서 필요한 기능만 가질 수 있도록 해야 한다.
```

---

## DIP 의존성 역전 원칙

> 상위 모델은 하위 모델에 의존하면 안된다. 둘 다 *추상화`에 의존해야 한다.
>
> 추상화는 세부 사항에 의존해서는 안된다. 세부사항은 추상화에 따라 달라진다.

하위 모델의 변경이 상위 모듈의 변경을 요구하는 위계 관계를 끊는다.

실제 사용관계는 그대로이지만, 추상화를 매개로 메시지를 주고 받으면서 관계를 느슨하게 만든다.

```
날아다닐 수 있는 강아지를 분양 받았는데

수영할 수 있는 강아지로 만들고 싶을 때

분양받은 강아지 자체를 바꾸는 것이 아닌

날개를 때고 손에 물갈퀴를 달아주는 것 처럼 관계를 느슨하게 만든다.
```

### 예시)

카드 결제, 계좌이체 결제를 하나하나 만드는 것이 아닌 결제 interface처럼 추상화된 인터페이스에 의존하도록하는 예시가 있다.


```java
😡 확장하기 싫게 생겼다. -> 유연하지 않다.

@RequestMapping(value = "/api/payment", method = RequestMethod.POST)
public void pay(@RequestBody PaymentDto.PaymentRequest req) {
    if(req.getType() == PayType.CARD) {
        cardPaymentService.pay(req);
    } else if (req.getType() == PayType.ACCOUNT) {
        accountPaymentService.pay(req);
    }
}
```

```java
😋 아주 코드 맛집이다.
인터페이스를 만들어 Card, Account가 상속받고 Factory를 통하여 Type에 맞는 것을 불러온다.
생성된 Service로 pay 메소드를 이용해 결제한다 !!

class PaymentController {
    @RequestMapping(value = "/api/payment", method = RequestMethod.POST)
    public void pay(@RequestBody PaymentDto.PaymentRequest req) {

        final PaymentService paymentService = paymentFactory.getType(req.getType());
        paymentService.pay(req);
    }
}

public interface PaymentService {
    void pay(PaymentDto.PaymentRequest req);
}

public class CardPaymentService implements PaymentService {

    @Override
    public void pay(PaymentDto.paymentRequest req) {
        cardApi.pay(req);
    }
}
```



# 2. 간결한 함수 작성하기

## SRP, OCP

> 함수를 작게 쪼개서 함수 내 `추상화 수준`을 동일하게 맞춘다.

한 곳에서 계산, 생성까지 두 가지 기능을 한다면 확장성이 좋지 않다.

위에 확장하기 싫어보이는 코드 (😡)에서 계산하는 방법에 카드, 계좌이체가 있다고 했을 때 계산 타입에 현금이 추가 된다면? 

코드가 매우 복잡해진다.

하지만

아래 코드 맛집(😋)처럼 

- 계산과 타입관리를 분리하고
- 타입에 대한 처리는 최대한 Factory에서만 처리해줘서 처리해준다.

## 함수 인수

> 인수의 갯수는 0 ~ 2개가 적당 !!

- 객체를 인자로 넘겨보자.

```java
// 객체를 인자로 넘기기
Circle makeCircle(double x, double y, double radius); // 😡
Circle makeCircle(Point center, double radius); // 💁‍♂️ 객체를 인자로 넘기니 너무 눈에 잘 들어오는구만 !
```

## 부수 효과 (Side Effect) 없는 함수

> Side Effect : 값을 반환하는 함수가 `외부 상태`를 변경하는 경우

함수와 관계 없이 비밀번호가 맞을 경우 Session을 initialize()해버린다.

이런 경우는 initialize()한다는 것을 함수명에 써주거나 아예 따로 뺴줘야 한다.

```java
public class UserLogin {
    
    public boolean checkPassword(String userName, String password) {
        User user = UserGateway.findByName(userName);
        if (user != User.NULL) {
            if ("Valid Password".equals(password)) {
                Session.initialize(); // 😡
                return true;
            }
        }
        return false;
    }
}
```

# 함수 리팩터링 (Simple Flow)

1. 기능을 구현하는 함수 작성
    - 길고, 복잡, 중복도 있다.

2. 테스트 코드 작성
    - 함수 내부의 분기와 엣지값마다 빠짐없이 테스트하는 코드를 짠다.

3. 리팩터링 한다.
    - 코드를 다듬고, 함수를 쪼개고, 이름을 바꾸고, 중복을 제거한다.