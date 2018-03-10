package universal;

import org.apache.xmlrpc.XmlRpcException;
import various.ClientServer;

import java.io.IOException;

public class ClientServer2 {

    public static void main(String[] args) throws IOException, XmlRpcException, InterruptedException {
        int myNumber = 2;
        new ClientServer(myNumber, myNumber + 1);
    }
}
