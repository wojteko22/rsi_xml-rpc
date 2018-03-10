package various;

import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

public class DefaultClientRunner {
    public static void main(String[] args) throws XmlRpcException, IOException, InterruptedException {
        CustomClient myClient = new CustomClient("http://localhost:3001");
        DefaultClient defaultClient = new DefaultClient(myClient);
        defaultClient.executeAll();
    }
}
