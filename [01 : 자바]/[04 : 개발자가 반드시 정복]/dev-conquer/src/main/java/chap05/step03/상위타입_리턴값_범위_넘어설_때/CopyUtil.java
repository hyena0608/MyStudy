package chap05.step03.상위타입_리턴값_범위_넘어설_때;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyUtil {

    public static void copy(InputStream is, OutputStream out) throws IOException {
        byte[] data = new byte[512];
        int len = -1;

        // 스트림의 끝 도달하면 -1 리턴
        while ((len = is.read()) != -1) {
            out.write(data, 0, len);
        }
    }
}
