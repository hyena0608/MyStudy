package clientserver.service.file;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class FileSender implements Runnable {
    private String filePath;
    private int port;

    public FileSender(int port) {
        this.port = port;
        System.out.println("기본 경로 : " + new File("").getAbsolutePath());
        System.out.print("전송할 파일 경로를 입력해주세요. : ");

        Scanner sc = new Scanner(System.in);
        this.filePath = sc.nextLine();

        System.out.println("선택된 파일 경로는 : " + filePath + " 입니다.");
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("상대방이 파일 요청을 받고 있습니다.");
            Socket socket = serverSocket.accept();
            System.out.println("상대방이 파일 요청을 받았습니다.");

            File sendFile = new File(filePath);

            FileInputStream fis = new FileInputStream(sendFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            OutputStream out = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);

            int read = 0;
            while ((read = bis.read()) != -1) {
                bos.write(read);
                System.out.print("#");
            }

            System.out.println("\n파일 전송 완료이 완료 되었습니다.");

            bos.close();
            bis.close();
            out.close();
            fis.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
