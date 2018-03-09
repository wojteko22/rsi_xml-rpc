import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Vector;

public class Client {
    private Cli cli = new Cli();

    public static void main(String[] args) throws XmlRpcException, IOException {
        Client client = new Client();
        client.tryToUseServer();
    }

    private void tryToUseServer() throws XmlRpcException, IOException {
        String url = cli.readHostname();
        int port = cli.readPort();
        try {
            useServer(url, port);
        } catch (IOException e) {
            handleRefusing(e, url);
        }
    }

    private void handleRefusing(IOException e, String url) throws XmlRpcException, IOException {
        if (connectionRefused(e, url)) {
            cli.printError();
            tryToUseServer();
        }
        else throw e;
    }

    private boolean connectionRefused(IOException e, String url) {
        return wrongPort(e) || wrongUrl(e, url);
    }

    private boolean wrongPort(IOException e) {
        return e.getMessage().contains("Connection refused: connect");
    }

    private boolean wrongUrl(IOException e, String url) {
        return e.getMessage().contains(url);
    }

    private void useServer(String url, int port) throws XmlRpcException, IOException {
        XmlRpcClient client = new XmlRpcClient(url, port);
        executeShow(client);
        executeMethod(client);
//        executeKnownMethods(client);
    }

    private void executeKnownMethods(XmlRpcClient client) throws XmlRpcException, IOException {
        MyServerClient myServerClient = new MyServerClient(client);
        myServerClient.executeAll();
    }

    private void executeMethod(XmlRpcClient client) throws XmlRpcException, IOException {
        String serverPrefix = "myServer.";
        String method = cli.readMethod();

        Vector<Object> params = new Vector<>();
        cli.addCustomParams(params);

        Object result = client.execute(serverPrefix + method, params);
        System.out.println("\n" + result);
    }

    private static void executeShow(XmlRpcClient client) throws XmlRpcException, IOException {
        String result = (String) client.execute("myServer.show", new Vector<>());
        System.out.println("\n" + result);
    }
}
