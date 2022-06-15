# 소켓을 이용한 채팅 프로그램 🚀

<br>

* [🤷 1) 왜 굳이 채팅 프로그램을 구현했어요 ?](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
    + [💁‍♂️ 1-1) **쓰레드 (Thread)**](#💁‍♂️-1-1-쓰레드-thread)
    + [💁‍♂️ 1-2) **객체 지향 프로그래밍**](#💁‍♂️-1-2-객체-지향-프로그래밍)
    + [💁‍♂️ 1-3) **UML (클래스 다이어그램)의 활용**](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
* [🤷 2) 오호, 그럼 경험을 더 구체적으로 알려주세요.](#🤷-2-오호-그럼-경험을-더-구체적으로-알려주세요)
    * [**쓰레드**](#쓰레드)
        + [2-1) 💡 **여러 클라이언트 관리 중 생기는 교착상태(deadlock) 이슈 해결**](#2-1-💡-여러-클라이언트-관리-중-생기는-교착상태deadlock-이슈-해결)
            + [2-1-1) 교착 상태 1차 이슈](#2-1-1-교착-상태-1차-이슈)
            + [2-1-2) 교착 상태 1차 이슈](#2-1-1-교착-상태-1차-이슈)
        + [2-2) 💡 **한 프로세스 내에서 콘솔과 소켓에서 송수신 이슈 해결**](#2-2-💡-한-프로세스-내에서-콘솔과-소켓에서-송수신-이슈-해결)
        + [2-3) 💡 **파일 송수신 시 기존 채팅 멈춤 이슈 해결**](#2-3-💡-파일-송수신-시-기존-채팅-멈춤-이슈-해결)
    * [**객체 지향 프로그래밍**](#객체-지향-프로그래밍)
        + [2-4) 🎈 **다형성의 활용**](#2-4-🎈-다형성의-활용)
        + [2-5) 🎈 **책임과 역할 분리를 위한 클래스, 메서드 분리**](#2-5-🎈-책임과-역할-분리를-위한-클래스-메서드-분리)
        + [2-6) 🎈 **람다와 스트림의 활용한 클라이언트 검색 기능**](#2-6-🎈-람다와-스트림의-활용한-클라이언트-검색-기능)
        + [2-7) 🎈 **디자인 패턴 (팩토리 패턴, 빌더 패턴) 이용**](#2-7-🎈-디자인-패턴-팩토리-패턴-빌더-패턴-이용)
        + [2-8) 🎈 **메모리 낭비 전역 변수 -> 지역 변수 변경**](#2-8-🎈-메모리-낭비-전역-변수---지역-변수-변경)
    * [**UML (클래스 다이어그램)**](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
        + [2-9) 💎 **설계 마무리까지의 시행착오**](#2-9-💎-설계-마무리까지의-시행착오)
* [3) 🙆‍♂️ 완료된 '소켓을 이용한 채팅 프로그램' 소개](#소켓을-이용한-채팅-프로그램-🚀)
    + [3-1) 요구사항](#3-1-요구사항)
    + [3-2) 기능 소개](#3-2-기능-소개)

<br>
<br>
<br>
<br>

- 채팅 프로그램을 구현하기 위해서는 기본적으로 소켓(socket)이라는 것을 사용하게 됩니다.

- 이 때 소켓은 프로세스(process) 간의 통신을 위해서 필요하며 

    프로토콜(protocol)에 따라 다른 종류의 소켓을 구현하여 제공합니다.

<br>


## 🤷 1) 왜 굳이 채팅 프로그램을 구현했어요 ?

<br>

저는 소켓을 이용한 채팅 프로그램 구현 프로젝트를 진행하면서

객체지향의 전체적인 맥락을 느낄 수 있었습니다. 

아래와 같이 쓰레드와 객체지향, 파일 I/O, UML 설계까지 경험할 수 있었죠.

<br>

### 💁‍♂️ 1-1) **쓰레드 (Thread)**
이러한 소켓으로 채팅 프로그램을 구현하려면 양방향 통신이 기본이 됩니다.

채팅은 한 번만 주고 받는 쪽지가 아니므로 계속해서 메시지 송수신 기능이 돌아가야 합니다.

이 때 첫 번째로 **쓰레드(Thread)** 를 사용하게 됩니다.

멀티쓰레드(multi thread)를 사용하면서

현 '소켓을 이용한 채팅 프로그램' 에서는 다음과 같은 경험을 얻게 되었습니다.
1. 여러 클라이언트 관리 중 생기는 교착상태(deadlock) 이슈 해결
1. 한 프로세스 내에서 콘솔과 소켓에서 송수신 이슈 해결
1. 파일 송수신 시 기존 채팅 멈춤 이슈 해결

<br>
<br>
<br>

### 💁‍♂️ 1-2) **객체 지향 프로그래밍**
채팅 프로그램을 구현하게 되면 계속해서 기능이 추가되게 됩니다.

기능 추가에 있어서 객체 지향적인 구현이지 않을 시에 큰 곤란을 겪을 수 있습니다.

때문에 다음과 같은 학습과 함께 '소켓을 이용한 채팅 프로그램" 프로젝트를 진행하였습니다.

1. 다형성의 활용
1. 책임과 역할 분리를 위한 클래스, 메서드 분리
1. 람다와 스트림의 활용한 클라이언트 검색 기능
1. 디자인 패턴 (팩토리 패턴, 빌더 패턴) 이용
1. 메모리 낭비 전역 변수 -> 지역 변수 변경

<br>
<br>
<br>

### 💁‍♂️ 1-3) **UML (클래스 다이어그램)의 활용**

프로젝트를 구현하면서 현재 설계가 옳바른지 확인하기 위해 

직접 UML (클래스 다이어그램)을 수정해나아가면서 진행하였습니다.

8회 정도 설계도를 갈아 엎으며 완성도를 높여나갔습니다. 



<br>
<br>
<br>

## 🤷 2) 오호, 그럼 경험을 더 구체적으로 알려주세요.

그럼 제가 '소켓을 이용한 채팅 프로그램'을 구현하면서 겪은 경험들을 아래에 소개 하겠습니다 !

<br>

## **쓰레드**

### 2-1) 💡 **여러 클라이언트 관리 중 생기는 교착상태(deadlock) 이슈 해결**

<br>

#### 2-1-1) 교착 상태 1차 이슈

> 교착상태(deadlock)이 발생하는 상황입니다.

<br>

Client를 관리하는 ClientManager입니다.

각 Client 측에서 수신된 메시지를 읽고, 수신된 메시지를 Client들에게 브로드캐스팅합니다.

서버에 등록된 각각의 클라이언트들이 `receiveMessage()`로 `in.readUTF()`를 하고 있던 도중 메시지를 받으면 `broadcastWaitingRoom(client, message)`메서드를 호출하였습니다.


```java
/**
 * 이전 상황 deadlock 이슈 발생
 */
 public class ClientManager extends Thread {
    private List<Client> waitingRoom;
    private Thread waitingRoomThread;

    public ClientManager() {
        waitingRoom = Collections.synchronizedList(new ArrayList<>());
        waitingRoomThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    handleWaitingRoom();
                }
            }
        };
        waitingRoomThread.start();
    }

    public void addClient(Client client) {...}

    private void handleWaitingRoom() {
        for (Client client : waitingRoom) {
            String message = client.receiveMessage();
            if (message != null && message.length() > 0) {
                broadcastWaitingRoom(client, message);
            }
        }
    }

    private void broadcastWaitingRoom(Client messageOwner, String message) {
        for (Client client : waitingRoom) {
            if (client != messageOwner) {
                client.sendMessage(message);
            }
        }
    }
}
```

<br>

이 때 Client는 Thread 입니다.

Client는 한 프로세스에서 각각의 쓰레드로 돌아가고 있습니다.

```java
public class Client extends Thread{
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    // ...

     public void sendMessage(String message) {
        try {
            System.out.println("[서버] : '" + message + "' 라는 메시지를 전송합니다.");
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receiveMessage() {
        String message = null;
        try {
            message = in.readUTF();
            System.out.println("[서버] : '" + message + "' 라는 메시지를 입력 받았습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

}
```

<br>



현재 상황은 아래 그림과 같습니다.



<br>
<br>
<br>

### 2-2) 💡 **한 프로세스 내에서 콘솔과 소켓에서 송수신 이슈 해결**

<br>
<br>
<br>

### 2-3) 💡 **파일 송수신 시 기존 채팅 멈춤 이슈 해결**

<br>
<br>
<br>

## **객체 지향 프로그래밍**

### 2-4) 🎈 **다형성의 활용**

<br>
<br>
<br>

### 2-5) 🎈 **책임과 역할 분리를 위한 클래스, 메서드 분리**
<br>
<br>
<br>

### 2-6) 🎈 **람다와 스트림의 활용한 클라이언트 검색 기능**

<br>
<br>
<br>

### 2-7) 🎈 **디자인 패턴 (팩토리 패턴, 빌더 패턴) 이용**

<br>
<br>
<br>

### 2-8) 🎈 **메모리 낭비 전역 변수 -> 지역 변수 변경**

<br>
<br>
<br>

## **UML (클래스 다이어그램)**

### 2-9) 💎 **설계 마무리까지의 시행착오**


---

<br>
<br>
<br>

## 3) 🙆‍♂️ 완료된 '소켓을 이용한 채팅 프로그램' 소개

<br>

### 3-1) 요구사항

<br>
<br>
<br>

### 3-2) 기능 소개

