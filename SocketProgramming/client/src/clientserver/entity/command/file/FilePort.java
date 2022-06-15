package clientserver.entity.command.file;

import java.util.LinkedList;
import java.util.Queue;

public class FilePort {
    private static Queue<Integer> portQ = new LinkedList<>();

    static {
        for (int port = 10000; port <= 20000; port++) {
            portQ.offer(port);
        }
    }

    private FilePort() {
    }

    public static Queue<Integer> getPortQ() {
        return portQ;
    }
}
