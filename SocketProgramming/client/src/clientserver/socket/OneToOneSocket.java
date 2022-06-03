package clientserver.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class OneToOneSocket implements Runnable {

    Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;



    @Override
    public void run() {

    }
}
