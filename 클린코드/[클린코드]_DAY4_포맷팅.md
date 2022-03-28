# 목차

- [개요](#개요)
- [포맷팅 왜 하는데?](#포맷팅-왜-하는데)
  * [가독성에 필수적이다](#가독성에-필수적이다)
  * [적절한 길이 유지 ~200, <500 lines](#적절한-길이-유지-200-500-lines)
  * [밀접한 개념은 서로 가까이](#밀접한-개념은-서로-가까이)
  * [Java Class Declarations](#java-class-declarations)
    + [Class 내부 코드 순서](#class-내부-코드-순서)
  * [Team Coding Convention](#team-coding-convention)

# 개요

개발자라면 이미 이런식으로 대부분 하고 있을 거다.

나 또한 이미 비슷한 형태로 해왔고 현재는 

Google Java Style Guide를 보면서 계속해서 연습해나아가고 있다.

# 포맷팅 왜 하는데?

## 가독성에 필수적이다

코드를 수월하게 읽어나갈 수 있다.

잘못 해석할 위험을 줄일 수 있다.

## 적절한 길이 유지 ~200, <500 lines

> ~200 lines < 500 lines

코드 길이를 200줄 정도로 제한은 아니다.

하지만 작은 파일이 더 이해하기 쉽다.

현업에서 이미 대부분의 코드가 200라인 정도를 유지한다.

코드 길이가 200 라인을 넘어간다면,

**클래스가 여러 개의 일을 하고 있을 수 있다 !! -> SRP 위배**

## 밀접한 개념은 서로 가까이

변수는 사용되는 위치에서 최대한 가까이 선언한다.

## Java Class Declarations

### Class 내부 코드 순서

1. static 변수
    - public -> protected -> package -> private

2. instance 변수
    - public -> protected -> package -> private

3. 생성자

4. 메서드
    - public 메서드에서 호출되는 private 메서드는 그 아래에 둔다.
    - 가독성 위주로 그룹핑 해준다.

## Team Coding Convention

개발 언어의 컨벤션이 우선 !!

> 애매한 부분은 팀 컨벤션을 따른다 !!

