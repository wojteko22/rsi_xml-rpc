package various;

import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

public class CustomClient {
    private final Cli cli = new Cli();
    private final XmlRpcClient client;
    private final String serverPrefix = "s.";

    CustomClient() throws IOException {
        client = connectViaCli();
    }

    CustomClient(String url) throws MalformedURLException {
        client = new XmlRpcClient(url);
    }

    CustomClient(String hostname, int port) throws MalformedURLException {
        client = new XmlRpcClient(hostname, port);
    }

    private XmlRpcClient connectViaCli() throws IOException {
        String hostname = cli.readString("Type hostname");
        int port = cli.readInt("Type port");
        return new XmlRpcClient(hostname, port);
    }

    void executeCustomMethod() throws XmlRpcException, IOException, InterruptedException {
        System.out.println("\n" + executeShow());
        String method = cli.readString("\nType method name");
        Vector<Object> params = cli.readParams();
        Object result = executeOnServer(method, params);
        System.out.println("\nResult of " + method + ": " + result);
    }

    String executeShow() throws XmlRpcException, IOException, InterruptedException {
        return (String) executeOnServer("show", new Vector<>());
    }

    Object executeOnServer(String method, Vector params) throws XmlRpcException, IOException, InterruptedException {
        try {
            return client.execute(serverPrefix + method, params);
        } catch (IOException e) {
            handleWrongHostname(e);
            return handleWrongPort(method, params, e);
        }
    }

    private void handleWrongHostname(IOException e) {
        if (wrongHost(e)) {
            System.out.println("You probably passed wrong hostname. Try localhost");
        }
    }

    private Object handleWrongPort(String method, Vector params, IOException e) throws InterruptedException, XmlRpcException, IOException {
        if (wrongPort(e)) {
            System.out.println("Waiting for connection");
            Thread.sleep(2000);
            return executeOnServer(method, params);
        } else throw e;
    }

    private boolean wrongHost(IOException e) {
        String host = client.getURL().getHost();
        return e.getMessage().contains(host);
    }

    private boolean wrongPort(IOException e) {
        return e.getMessage().contains("Connection refused: connect");
    }

    void executeAsyncOnServer(String method, Vector params, AsyncCallback callback) {
        client.executeAsync(serverPrefix + method, params, callback);
    }
}
