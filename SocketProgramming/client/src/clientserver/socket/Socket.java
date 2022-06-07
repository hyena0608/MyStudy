package clientserver.socket;

import java.io.DataOutputStream;

public interface Socket {

    abstract DataOutputStream takeOut();
}
