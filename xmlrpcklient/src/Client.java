import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

public class Client {
    private final Cli cli = new Cli();
    private final XmlRpcClient client;
    private final String serverPrefix = "s.";

    Client() throws XmlRpcException, IOException {
        client = connectViaCli();
    }

    Client(String url) throws MalformedURLException {
        client = new XmlRpcClient(url);
    }

    private XmlRpcClient connectViaCli() throws XmlRpcException, IOException {
        String url = cli.readHostname();
        int port = cli.readPort();
        try {
            return new XmlRpcClient(url, port);
        } catch (IOException e) {
            return handleRefusing(e, url);
        }
    }

    private XmlRpcClient handleRefusing(IOException e, String url) throws XmlRpcException, IOException {
        if (connectionRefused(e, url)) {
            cli.printError();
            return connectViaCli();
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

    void executeKnownMethods() throws XmlRpcException, IOException {
        DefaultServerClient defaultServerClient = new DefaultServerClient(client);
        defaultServerClient.executeAll();
    }

    void executeCustomMethod() throws XmlRpcException, IOException {
        executeShow();
        String method = cli.readMethod();

        Vector<Object> params = new Vector<>();
        cli.addCustomParams(params);

        Object result = client.execute(serverPrefix + method, params);
        System.out.println("\nResult of " + method + ": " + result);
    }

    private void executeShow() throws XmlRpcException, IOException {
        String result = (String) client.execute(serverPrefix + "show", new Vector<>());
        System.out.println("\n" + result);
    }
}
