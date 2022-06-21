# 소켓을 이용한 채팅 프로그램 🚀

<br>

- [소켓을 이용한 채팅 프로그램 🚀](#소켓을-이용한-채팅-프로그램-🚀)
  * [🤷 1) 왜 굳이 채팅 프로그램을 구현했어요 ?](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
    + [💁‍♂️ 1-1) 쓰레드 (Thread)](#💁‍♂️-1-1-쓰레드-thread)
    + [💁‍♂️ 1-2) 객체 지향 프로그래밍](#💁‍♂️-1-2-객체-지향-프로그래밍)
    + [💁‍♂️ 1-3) UML (클래스 다이어그램)의 활용](#💁‍♂️-1-3-uml-클래스-다이어그램의-활용)
  * [🤷 2) 오호, 그럼 경험을 더 구체적으로 알려주세요.](#🤷-2-오호-그럼-경험을-더-구체적으로-알려주세요)
  * [쓰레드](#쓰레드)
    + [2-1) 💡 여러 클라이언트 관리 중 생기는 교착상태(deadlock) 이슈 해결](#2-1-💡-여러-클라이언트-관리-중-생기는-교착상태deadlock-이슈-해결-)
        + [2-1-1) 교착 상태 1차 이슈 발생 (at server)](#2-1-1-교착-상태-1차-이슈-발생-at-server)
        + [2-1-2) 교착 상태 1차 이슈 해결](#2-1-2-교착-상태-1차-이슈-해결)
            + [2-1-2-1) 채팅 메인 서버 측 Client 소켓 관리](#2-1-2-1-채팅-메인-서버-측-client-소켓-관리)
            + [2-1-2-2) 채팅 메인 서버 측 Client의 메시지 송수신 기능](#2-1-2-2-채팅-메인-서버-측-client의-메시지-송수신-기능)
            + [2-1-2-3) 교착 상태 해결 이후의 프로세스(process) 형태](#2-1-2-3-교착-상태-해결-이후의-프로세스process-형태)
    + [2-2) 채널, 방 임계 영역 설정하기](#2-2-채널-방-임계-영역-설정하기)
    + [2-3) 💡 파일 송수신 기능 구현](#2-3-💡-파일-송수신-기능-구현)
  * [객체 지향 프로그래밍](#객체-지향-프로그래밍)
    + [2-4) 🎈 다형성의 활용 (+ 정적 팩토리 패턴)](#2-4-🎈-다형성의-활용--정적-팩토리-패턴)
    + [2-5) 🎈 책임과 역할 분리를 위한 클래스, 메서드 분리](#2-5-🎈-책임과-역할-분리를-위한-클래스-메서드-분리)
    + [2-6) 🎈 forEach문을 활용한 클라이언트 검색](#2-6-🎈-foreach문을-활용한-클라이언트-검색)
  * [UML (클래스 다이어그램)](#uml-클래스-다이어그램)
    + [2-7) 💎 설계 마무리까지의 시행착오](#2-7-💎-설계-마무리까지의-시행착오)
  * [3) 🙆‍♂️ 완료된 '소켓을 이용한 채팅 프로그램' 소개해주세요 !](#3-🙆‍♂️-완료된-소켓을-이용한-채팅-프로그램-소개해주세요)
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

이 때 락(lock)은 단 하나만 가질 수 있는데

때문에 **교착 상태(Deadlock)** 가 발생하게 되었습니다. 


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

<br>
<br>

ps.(*밑에 임계영역이 왜 이렇게 많이 설정 되어 있을까 ?. 아래에서 바로 알아봅시다 !!*

*[2-2) 채널, 방 임계영역 설정하기]())*

<img src="image\2-1-2-3_교착상태_해결_이후_프로세스_상태_1.png" />

<img src="image\2-1-2-3_교착상태_해결_이후_프로세스_상태_2.png" />

<br>
<br>
<br>

### 2-2) **채널, 방 임계 영역 설정하기**

(현재 내용은 채팅 메인 서버 측의 내용입니다.)

위에 채널, 방에 대한 임계 영역(critical section)을 볼 수 있습니다.

왜 이런식으로 설정하게 되었을까요 ?

그 이유는 아래와 같습니다.

<br>

먼저 사용자 A, B가 있다고 가정하겠습니다.

- 상황 1) A, B가 동시에 같은 채널에 접근 했을 때

    A(채널1, 방1)과 B(채널2, 방3)가 동시에 A(채널2, 방2), B(채널2, 방3)로 이동할 경우에 채널 2에서 동시 쓰레드 접근이 발생하여 오류가 발생합니다. 때문에 채널 자체에도 임계 영역을 설정해서 동시 접근 발생에 대하여 
    
    즉, 채널 전체를 동기화 시켜줘야 합니다.

- 상황 2) A, B가 동시에 같은 방에 접근 했을 때

    위 상황과 마찬가지로 동시에 방에 접근 했을 때에도 같은 데이터 영역을 동시에 사용하려 하므로 데드락이 걸립니다. 
    
    때문에 각 채널마다 동기화 시켜줘야 합니다.

- 상황 3) A, B 메시지 수신

    같은 방 안에 A, B가 동시에 메시지를 수신한다는 것은 이 또 한 같은 데이터 영역을 동시에 이용하게 됩니다. 

    이 경우는 각 방마다 동기화 시켜줘야 합니다.


이보다 더 많은 경우들이 있지만 다음과 같이 

1. 채널 전체
1. 각 채널
1. 각 방

에 대하여 동기화 시켜주어야지 같은 데이터 영역을 동시에 접근하지 않게 됩니다.

<br>
<br>
<br>

### 2-3) 💡 **파일 송수신 기능 구현**

<img src="image\2-3_파일송수신_1.png">

<br>

1. **동작 설명**

    클라이언트가 귓속말 상태일 경우에 '/파일 전송'이라는 명령어를 통해 파일을 전송할 수 있습니다.

    FileSender에서 파일 전송을 위한 ServerSocket을 새로 오픈하고 

    현재 귓속말 상대에게 port와 ip를 전송해줍니다.

    port와 ip를 받은 상대방은 FileReceiver를 통해 서버소켓(ServerSocket)에 접속하여 파일 수신을 대기합니다.

    송신자가 파일 전송할 경로를 입력하면 수신자는 파일명을 입력하고 다운로드가 시작됩니다.

1. **FileSender, FileReceiver가 Thread인 이유**

    파일을 송수신하는 도중에도 채팅방 메시지를 수신 받기 위해서 Thread를 취했습니다.

<br>

실제 파일 송수신과 채팅방의 동작은 아래 그림과 같이 작동하고 있습니다.

왼편은 채팅 메시지 수신이 계속 일어나는 것을 의미하고

오른편은 파일 송수신을 나타내고 있습니다.

<img src="image\2-3_파일송수신_2.png">

<br>
<br>
<br>

## **객체 지향 프로그래밍**

<br>
<br>

### 2-4) 🎈 **다형성의 활용** (+ 정적 팩토리 패턴)

채팅과 설정을 하기 위해 계속 객체를 생성해야 하는 상황입니다.

이러한 상황에서는 생성자 혹은 정적 팩토리 메서드 둘 중 하나를 선택해야 했습니다.

근데 왜 ? 정적 팩토리 메서드를 선택 했을까요?

- 저는 여러 번의 객체 생성이 필요했고
- 호출할 때 마다 새로운 객체를 생성할 필요가 없어졌습니다.
- 생성자가 아닌 정적 팩토리 메서드는 가독성이 좋고 객체지향적인 프로그래밍을 도와줍니다.
  
  가독성이 좋다는 것을 설명하자면 생성자 보다 더 구체적인 이름을 가질 수 있다는 것입니다.

  메서드를 호출하는 것으로 직관적인 기능을 알 수 있죠.

정적 팩토리 메서드를 만들어 아래 그림과 같은 상황이 되었습니다. (채팅 서버)

<img src="image\2-4_다형성의활용_1.png">

<br>
<br>

> 🤷‍♂️ 아니 근데 쓰레드 상황에서 같은 정적 Instance를 사용하면 같은 데이터 영역이라 위험하지 않나요 ??

<br>

그래서 저는 **DCL (Double-Checking Locking)** 방법을 사용했습니다.

간단하게 설명하자면 인스턴스가 생성되어 있지 않는 상태라면 인스턴스를 가져와서 사용하지만

인스턴스가 이미 생성되어 있는 상태라면 **동기화(synchronization)** 시켜서 데이터 영역의 동시 접근 위험성을 제거해줍니다.

동기화가 필요한 상황에서만 동기화 시키므로 무조건적으로 동기화 시키는 것보다 효율적입니다.

<br>

또한 **volatile** 키워드를 사용했고 이는

자바 변수를 Main Memory에 올려 동시화 문제를 해결 했습니다.

만약 volatile 키워드를 사용하지 않았다면 cpu Cache에만 변수가 반영되어 동기화 문제가 생길 수도 있기 때문입니다.

<br>

때문에 아래와 같이 구현 했습니다. (RoomChatting Example)

- static
- volatile
- synchronized

에 주의해주세요.

```java
public class RoomChatting implements Chatting {
    private static volatile RoomChatting instance;

    private RoomChatting() {}

    public static RoomChatting getInstance() {
        if (instance == null) {
            synchronized (RoomChatting.class) {
                if (instance == null) {
                    instance = new RoomChatting();
                }
            }
        }
        return instance;
    }
}
```

<br>
<br>
<br>

### 2-5) 🎈 **책임과 역할 분리를 위한 클래스, 메서드 분리**

다음과 같이 분리 하였습니다. 

**1. 채팅 서버**

- 클래스 변경
    - Client -> UserSocket
    - Client -> User
    - ClientManager -> UserSocketMessageHandler
    - ClientManager -> ChannelHandler
    - 
- 기능 분할
    - UserSocket, User
        - 유저 소켓 정보와 유저 정보를 분리하여 관리했습니다.
    - UserSocketMessageHandler, ChannelHandler
        - 채널 관리와 메시지 송수신 관리 기능을 분리하였습니다.

<br>

**2. 클라이언트 서버**

<img src="image\2-5_책임분리_1.png">

<br>

- 클래스 변경
    - ClientReceiver -> SocketMessageHandlerImpl (<\<Interface>> MessageHandler)
    - ClientReader -> ConsoleMessageHandlerImpl (<\<Interface>> MessageHandler)
    - ClientSender -> SocketMessageHandlerImpl (<\<Interface>> MessageHandler)
    - MessageParser -> <\<Interface>> Chatting, <\<Interface>> Setting

    <br>

    기존 클래스를 보면 여러 역할을 하고 있는 것을 볼 수 있습니다.

    ClientSender 클래스 같은 경우 사용자 콘솔을 입력 받고 메시지를 메인 서버에 보내기 까지 합니다.

    이러한 역할을 나눠서 메인 채팅 서버용 클래스, 클라이언트 서버용 클래스를 나눠서 재구현하였습니다. 


- 기능 분할
    - 정적 팩토리 메서드를 이용하여 상황에 맞는 각각의 기능을 가진 객체를 불러와 처리할 수 있게 되었습니다. 




<br>
<br>
<br>

### 2-6) 🎈 **forEach문을 활용한 클라이언트 검색**

for문을 중첩해서 사용하는 것이 아닌 forEach문을 사용했습니다.

```java
public void broadcastMessage(MessageObject messageObject) {
    String channelTitle = messageObject.getUser().getChannelTitle();
    String roomTitle = messageObject.getUser().getRoomTitle();
    String receiver = messageObject.getUser().getUsername();
    String messageJson = UserSocketMessageParser.toJson(messageObject);

    channelMap.get(channelTitle)
            .get(roomTitle)
            .getUserSocketList()
            .forEach(userSocket -> {
                if (!userSocket.getUser().getUsername().equals(receiver)) {
                    try {
                        userSocket.getOut().writeUTF(messageJson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ;
            });
}
```
<br>
<br>
<br>

## **UML (클래스 다이어그램)**

### 2-7) 💎 **설계 마무리까지의 시행착오**

<br>

클래스 다이어그램을 설계하면서 많은 시행 착오를 겪었습니다.

클라이언트 서버 설계도만 5번 이상 갈아 엎었고 생각보다 더 많은 시간을 소비했습니다.

하지만 피드백을 받으며 점차적으로 안정적인 설계도가 나오고 기능이 추가 되어도 설계도의 틀이 변하지 않는 모습을 볼 수 있게 되었습니다.

아래는 클라이언트 서버 클래스 다이어그램 시행착오 사진들입니다.

<img src="image\2-7_UML_1.png">

<br>

저는 클래스 다이어그램을 설계와 동시에 구현을 같이 병행 했습니다.

처음에는 설계가 완성되면 구현을 시작했는데 막상 시작하니 클래스 다이어그램대로 구현 하기 힘든 경우가 빈번 했습니다.

때문에 설계 도중에 구현을 계속해서 병행해주어 구조적으로 괜찮은지 확인하면서 진행하였습니다.

<br>

아래는 클라이언트 서버 클래스 다이어그램 입니다.

<img src="image\2-7_UML_2.png">

<br>

아래는 메인 채팅 서버 클래스 다이어그램 입니다.

<img src="image\2-7_UML_3.png">





---

<br>
<br>
<br>

## 3) 🙆‍♂️ 완료된 '소켓을 이용한 채팅 프로그램' 소개해주세요 !

<br>

### 3-1) 요구사항

<br>

**메인 채팅 서버**
- 메인 채팅 서버를 오픈합니다.
- 메인 채팅 서버에 있는 채널, 방 정보를 클라이언트 서버에 전달합니다.
- 클라이언트의 사용자 정보를 받아 저장합니다.
- 클라이언트 서버로부터 메시지를 수신합니다.
- 수신한 메시지를 메시지 타입, 유저 타입에 따라 구분하여 기능을 수행합니다.
- 메시지 타입이 귓속말이 경우에 해당 파트너에게만 메시지를 전달합니다.
- 메시지 타입이 귓속말 Disconnect일 경우 파트너를 삭제합니다.
- 메시지 타입이 채팅일 경우에 해당 방에 메세지를 전달합니다.
- 메시지 타입이 파일일 경우에 해당 설정 메시지를 파트너에게 전달합니다.

**클라이언트 서버**
- 클라이언트는 메인 채팅 서버에 접속할 수 있습니다.
- 클라이언트 접속 시 사용자 정보 입력, 클라이언트 서버에 저장, 메인 채팅 서버에도 사용자 정보를 전달합니다.
- 방채팅 시에 메시지와 사용자 정보를 클라이언트에게 JSON 형태로 전달합니다.
- '/귓속말' 콘솔 입력 후 상대방 닉네임 입력시 귓속말이 시작됩니다.
- 귓속말 상대방이 된 클라이언트는 귓속말이 잡혔다는 말과 동시에 파트너로 취급됩니다.
- 귓속말 도중에는 방채팅 내용을 계속해서 볼 수 있습니다.
- '/종료' 콘솔 입력 시에 귓속말이 종료되며 파트너가 끊깁니다.
- 귓속말 상태에서 '/파일전송' 콘솔 입력 시에 서버소켓을 오픈합니다.
- 서버소켓을 오픈하고 해당 ip, port를 서버에게 넘겨줍니다.
- 귓속말 파트너인 클라이언트가 ip, port가 받아지면 해당 서버소켓에 연결합니다.
- 송신하는 클라이언트가 보낼 파일 경로를 콘솔에 입력합니다.
- 수신하는 클라이언트가 저장될 파일명을 입력합니다.
- 파일은 새로운 서버소켓을 통해 송수신 됩니다.
- 귓속말이 아닌 상태에서 파일 전송을 할 수 없습니다.


<br>
<br>
<br>

### 3-2) 기능 소개


<br>

기능은 크게 4가지가 있습니다.

- 채널, 방 입장 기능
- 방 채팅 기능
- 귓속말 요청/송수신/종료 기능
- 파일 송수신 기능

<br>

아래 이미지는 실제 콘솔 동작 모습입니다.

<img src="image\3-2_기능소개.png">

