import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

public class ClientRunner {
    public static void main(String[] args) throws XmlRpcException, IOException {
        Client myClient = new Client();
        myClient.executeCustomMethod();
        myClient.executeKnownMethods();
    }
}
