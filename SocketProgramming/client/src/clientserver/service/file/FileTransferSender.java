package clientserver.service.file;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferSender implements Runnable {
    String filePath;

    public FileTransferSender(String filePath) {
        this.filePath = filePath;
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
