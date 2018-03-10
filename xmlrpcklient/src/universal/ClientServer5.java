package universal;

import org.apache.xmlrpc.XmlRpcException;
import various.ClientServer;

import java.io.IOException;

public class ClientServer5 {

    public static void main(String[] args) throws IOException, XmlRpcException, InterruptedException {
        int myNumber = 5;
        new ClientServer(myNumber, myNumber - 4);
    }
}
