package various;

import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

public class ClientRunner {
    public static void main(String[] args) throws XmlRpcException, IOException {
        CustomClient myClient = new CustomClient("http://localhost:3001");
        myClient.executeCustomMethod();
        myClient.executeKnownMethods();
    }
}
