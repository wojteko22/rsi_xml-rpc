package various;

import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

public class CustomClientRunner {
    public static void main(String[] args) throws XmlRpcException, IOException, InterruptedException {
        CustomClient myClient = new CustomClient();
        myClient.executeCustomMethod();
    }
}
