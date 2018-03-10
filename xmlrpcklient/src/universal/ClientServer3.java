package universal;

import org.apache.xmlrpc.XmlRpcException;
import various.ClientServer;

import java.io.IOException;

public class ClientServer3 {

    public static void main(String[] args) throws IOException, XmlRpcException, InterruptedException {
        int myNumber = 3;
        new ClientServer(myNumber, myNumber + 1);
    }
}
