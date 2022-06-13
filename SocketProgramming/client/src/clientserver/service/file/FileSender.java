package clientserver.service.file;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileSender implements Runnable {
    String filePath;

    public FileSender() {
        System.out.print("전송할 파일 경로를 입력해주세요. : ");
        Scanner sc = new Scanner(System.in);
        this.filePath = sc.nextLine();
        System.out.println("선택된 파일 경로는 : " + filePath + " 입니다.");
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            Socket socket = serverSocket.accept();

            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);

            OutputStream out = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);

            System.out.println("read");
            int read = 0;
            while ((read = bis.read()) != -1) {
                bos.write(read);
            }

            System.out.println("파일 전송 완료");

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
