package clientserver.service.file;

import java.io.*;
import java.net.Socket;

public class FileTransferReceiver implements Runnable {

    // TODO : 서버 아이피, 포트를 서버에서 직접 전달 받아야함.
    private final String ip = "localhost";
    private final int port = 9999;
    private String fileName;
    private Socket socket;
    private InputStream in;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;

    public FileTransferReceiver(String fileName) {
        try {
            this.fileName = fileName;
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

            File f = new File("Down");
            if (!f.isDirectory()) {
                f.mkdir();
            }

            fos = new FileOutputStream("Down" + fileSeparator + fileName);
            bos = new BufferedOutputStream(fos);
            int r = 0;
            while ((r = bis.read()) != -1) {
                bos.write(r);
            }

            System.out.println("====================> Down" + fileSeparator + fileName);

        } catch (FileNotFoundException e) {
            System.out.println("예외: " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                fos.close();
                in.close();
                bis.close();
                bos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
