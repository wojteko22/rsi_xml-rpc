import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

public class ClientRunner {
    public static void main(String[] args) throws XmlRpcException, IOException {
//        Client myClient = new Client();
        Client myClient = new Client("http://localhost:3001");
        myClient.executeCustomMethod();
        myClient.executeKnownMethods();
    }
}
