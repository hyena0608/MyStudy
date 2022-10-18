package chap05.step03.상위타입_리턴값_범위_넘어설_때;

import java.io.IOException;
import java.io.InputStream;

public class SatanInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        // 데이터 없을 때 0 리턴
        return 0;
    }
}
