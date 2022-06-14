package clientserver.service.file;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FileReceiver implements Runnable {

    // TODO : 서버 아이피, 포트를 서버에서 직접 전달 받아야함.
    private final String ip = "localhost";
    private final int port = 9999;
    private String fileName;
    private Socket socket;
    private InputStream in;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;

    public FileReceiver() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("기본 경로 : " + new File("download").getAbsolutePath());
            System.out.print("저장할 파일명을 입력하세요 : ");

            // TODO : 스캐너 인식..??
            this.fileName = sc.nextLine();
            System.out.println("fileName" + " = " + fileName);

            socket = new Socket(ip, port);
            in = socket.getInputStream();
            bis = new BufferedInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            String fileSeparator = System.getProperty("file.separator");
            File file = new File("download");
            if (!file.isDirectory()) {
                file.mkdir();
            }
                System.out.println(file.getAbsolutePath() + "\\" + this.fileName);
            fos = new FileOutputStream(file.getAbsolutePath() + fileSeparator + this.fileName);
            bos = new BufferedOutputStream(fos);
            int read = 0;

            while ((read = bis.read()) != -1) {
                bos.write(read);
            }

            System.out.println("##################### download.jpg" + "\\" + this.fileName);

        } catch (FileNotFoundException e) {
            System.out.println("예외: " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                fos.close();
                in.close();
//                bos.close();
                bis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
