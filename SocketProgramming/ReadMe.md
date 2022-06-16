# 소켓을 이용한 채팅 프로그램 🚀

<br>

* [🤷 1) 왜 굳이 채팅 프로그램을 구현했어요 ?](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
    + [💁‍♂️ 1-1) 쓰레드 (Thread)](#💁‍♂️-1-1-쓰레드-thread)
    + [💁‍♂️ 1-2) 객체 지향 프로그래밍](#💁‍♂️-1-2-객체-지향-프로그래밍)
    + [💁‍♂️ 1-3) UML (클래스 다이어그램)의 활용](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
* [🤷 2) 오호, 그럼 경험을 더 구체적으로 알려주세요.](#🤷-2-오호-그럼-경험을-더-구체적으로-알려주세요)
    * [쓰레드](#쓰레드)
        + [2-1) 💡 여러 클라이언트 관리 중 생기는 교착상태(deadlock) 이슈 해결](#2-1-💡-여러-클라이언트-관리-중-생기는-교착상태deadlock-이슈-해결)
            + [2-1-1) 교착 상태 1차 이슈](#2-1-1-교착-상태-1차-이슈)
            + [2-1-2) 교착 상태 1차 이슈](#2-1-1-교착-상태-1차-이슈)
        + [2-2) 💡 한 프로세스 내에서 콘솔과 소켓에서 송수신 이슈 해결](#2-2-💡-한-프로세스-내에서-콘솔과-소켓에서-송수신-이슈-해결)
        + [2-3) 💡 파일 송수신 시 기존 채팅 멈춤 이슈 해결](#2-3-💡-파일-송수신-시-기존-채팅-멈춤-이슈-해결)
    * [객체 지향 프로그래밍**](#객체-지향-프로그래밍)
        + [2-4) 🎈 다형성의 활용**](#2-4-🎈-다형성의-활용)
        + [2-5) 🎈 책임과 역할 분리를 위한 클래스, 메서드 분리](#2-5-🎈-책임과-역할-분리를-위한-클래스-메서드-분리)
        + [2-6) 🎈 람다와 스트림의 활용한 클라이언트 검색 기능](#2-6-🎈-람다와-스트림의-활용한-클라이언트-검색-기능)
        + [2-7) 🎈 디자인 패턴 (팩토리 패턴, 빌더 패턴) 이용](#2-7-🎈-디자인-패턴-팩토리-패턴-빌더-패턴-이용)
        + [2-8) 🎈 메모리 낭비 전역 변수 -> 지역 변수 변경](#2-8-🎈-메모리-낭비-전역-변수---지역-변수-변경)
    * [UML (클래스 다이어그램)](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
        + [2-9) 💎 설계 마무리까지의 시행착오](#2-9-💎-설계-마무리까지의-시행착오)
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

### 2-1-1) 교착 상태 1차 이슈 발생 (at server)

> 교착상태(deadlock)이 발생하는 상황입니다.

<br>

Client를 관리하는 ClientManager입니다.

각 Client 측에서 수신된 메시지를 읽고, 수신된 메시지를 Client들에게 브로드캐스팅합니다.

서버에 등록된 각각의 클라이언트들이 `receiveMessage()`로 `in.readUTF()`를 하고 있던 도중 

메시지를 받으면 `broadcastWaitingRoom(client, message)`메서드를 호출하였습니다.


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



현재 상황은 아래와 같습니다.

`List<Client> waitingRoom = Collections.synchronizedList(new ArrayList<>());
`

가 선언되어 있습니다.

`waitingRoom`는 진행 중인 작업을 다른 쓰레드가 간섭하지 못하도록 동기화(synchronized) 하여 

`waitingRoom`을 **임계영역(critical section)** 으로 지정해주고

**락(lock)** 을 주었지만 락(lock)을 가지고 있는 객체가 아닌 다른 객체의 코드도 실행되고 있습니다.

때문에 **교착 상태(Deadlock)** 오류가 발생하게 되었습니다. 


아래 현 상황에 대한 그림을 그렸습니다.

<img src="image\2-1-1_교착상태_1차_이슈_1.png" alt="2-1-1 교착 상태 1차 이슈 사진" />

<img src="image\2-1-1_교착상태_1차_이슈_2.png" alt="2-1-1 교착 상태 1차 이슈 사진" />


<br>
<br>
<br>

### 2-1-2) 교착 상태 1차 이슈 해결

임계 영역(critical section)에서 락(lock)을 두 쓰레드에서 동시에 사용하려고 하니 문제가 생겼습니다.

때문에 저는 다음과 같이 해결하였습니다.

(현재 내용은 채팅 메인 서버 측의 내용입니다.)

- ***채팅 메인 서버 측 Client 소켓 관리***

- ***채팅 메인 서버 측 Client의 메시지 송수신 기능***

<br>

### 2-1-2-1) 채팅 메인 서버 측 Client 소켓 관리

처음에 ClientManager가 Client 전체를 `List<Client>`로 감싸서 관리하였습니다.

때문에 for문을 사용해서 각각의 Client의 `DataInputStream`에 `readUTF()` 메서드를 사용했을 때 

메시지를 수신 받는다면 다시 for문을 활용하여 Client의 `DataOutputStream`에 `writeUTF()` 메서드를 사용하여 각각의 클라이언트 서버 측에 메시지를 송신하였습니다.

<br>

이 때 임계 영역에서의 락(lock)을 가지고 있지 않은 쓰레드가 사용하려하면서 **교착 상태**가 발생하게 됐습니다.

때문에 **락(lock)** 순차적으로 가질 수 있게 **ClientManager**를 없애고 **Client** 각각이 메시지를 송수신할 수 있도록 처리하여 해결했습니다.

대신에 채팅 메인 서버에서 ClientChannel을 만들어 각각의 클라이언트 소켓을 저장하는 방식으로 구현하였습니다. 

기존 ClientManager를 삭제하고 ClientChannel은 아래와 같습니다.

```java
public class ClientChannel extends Thread {

    static HashMap<String, HashMap<String, List<Client>>> channelMap;

    public ClientChannel() {...}

    public void addClientToRoom() {...}
    
    // 서버 소켓 오픈
    public static void main(String[] args) {...}

}
```

<br>
<br>
<br>

### 2-1-2-2) 채팅 메인 서버 측 Client의 메시지 송수신 기능

먼저 Client에 클라이언트 서버 쪽의 메시지의 수신과

서버 측에서 클라이언트 서버에 보내는 메세지의 송신 기능이 있습니다.

<br>

채팅 메인 서버 측에서 메시지의 수신 같은 경우는 클라이언트의 소켓(Socket)을 서버소켓(ServerSocket)에서 받아 while 문으로 계속해서 메시지를 받아줘야 합니다.

<br>

아래 코드는 클라이언트 측에서 송신한 메시지의 **채팅 메인 서버의 수신 코드** 입니다.

```java
private DataInputStream in = new DataInputStream(socket.getInputStream);

// try-catch 생략
while (in != null) {
    String message = in.readUTf();
}
```

아래 코드는 클라이언트 측에게 메시지를 수신하는 **채팅 메인 서버의 코드** 입니다.

```java
private DataOutputStream out = new DataOutputStream(socket.getOutputStream);

// try-catch 생략
if (message != null && message != "") {
    out.writeUTF(message);
}
```

이러한 메시지 송수신 기능이 있던 기존 ClientManager 클래스를 삭제한 후 Client 클래스에 기능을 할당하였습니다.

Client 코드를 다음과 같이 작성하였습니다.

```java
public class implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public final String channelName;
    public final String roomName;

    public Client(Socket socket, String channelName, String roomName) {...}

    // try-catch 생략
    public void getMessateFromClientServer() {
        String messageFromClientServer = this.in.readUTF();
        broadcastMessage(this, messageFromClientServer);
    }

    public void broadcastMessage(Client client, String messageFromClientServer) {
        for (...) {
            // try-catch 생략
            if (!eachClient.equals(client)) {
                eachClient.out.writeUTF(messageFromClientServer);
            }
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            getMessageFromClientServer();
        }
    }
}
```

<br>
<br>

### 2-1-2-3) 교착 상태 해결 이후의 프로세스(process) 형태

- 채팅 메인 서버 측 Client 소켓 관리
- 채팅 메인 서버 측 Client의 메시지 송수신 기능

> 위에서 두 가지 방법으로 코드를 수정하면서 현 쓰레드(Thread)가 동작하면서 다른 쓰레드를 건드리지 않게 되었습니다.

때문에 교착 상태가 없어졌으며 

프로세스 내부는 다음과 같은 그림이 되었습니다. 

(기존은 대기실(waitingRoom) 하나 였지만 현재는 채널(channel)과 방(room)으로 이루어져 있습니다.)

ps.(*밑에 그림은 (채널에 쓰레드 x) 잘못된 점이 있고 이하 아래에서 해결하는 글이 있습니다.*

*[2-2) 마구잡이식 쓰레드 처리하기](#2-2-마구잡이식-쓰레드thread-처리하기))*

<img src="image\2-1-2-3_교착상태_해결_이후_프로세스_상태_1.png" />

<img src="image\2-1-2-3_교착상태_해결_이후_프로세스_상태_2.png" />

<br>
<br>
<br>

### 2-2) **마구잡이식 쓰레드(Thread) 처리하기**

(현재 내용은 채팅 메인 서버 측의 내용입니다.)

위에 채널, 방과 관련된 이미지에 잘못된 점이 있습니다.

바로 쓰레드일 필요가 없는데 쓰레드가 구현된 것들이 있습니다.

<br>

*각 채널은 단지 방들을 모여주는 저장소일 뿐입니다. 따로 Thread를 할당할 필요가 없습니다.*

<br>





<br>
<br>
<br>

### 2-3) 💡 **파일 송수신 기능 구현**

<br>
<br>
<br>

## **객체 지향 프로그래밍**

### 2-4) 🎈 **다형성의 활용**

<br>
<br>
<br>

### 2-5) 🎈 **책임과 역할 분리를 위한 클래스, 메서드 분리**

이러한 두 기능을 다음과 같이 분할 하였습니다.

- 클래스명 변경
    - Client -> UserSocket
    - ClientManager -> UserSocketMessageHandler
- 기능 분할
    - UserSocket
        - 클라이언트 서버측의 메시지를 수신한다.
        - 메시지가 수신된 경우 UserSocketMessageHandler에게 메시지를 넘겨준다.
    - UserSocketMessageHandler

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

## 3) 🙆‍♂️ 완료된 '소켓을 이용한 채팅 프로그램' 소개해주세요 !

<br>

### 3-1) 요구사항

<br>
<br>
<br>

### 3-2) 기능 소개

