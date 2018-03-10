package various;

import org.apache.xmlrpc.WebServer;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Vector;

public class ClientServer {
    private final CustomClient client;
    private final int myNumber;
    private final Cli cli = new Cli();

    public ClientServer(int myNumber, int serverNumber) throws InterruptedException, IOException, XmlRpcException {
        this.myNumber = myNumber;
        int defaultPort = 3000;
        serve(defaultPort + myNumber);
        client = connectTo(defaultPort + serverNumber);
        executeSomewhere();
    }

    private void serve(int port) {
        String name = "s";
        WebServer server = new WebServer(port);
        server.addHandler(name, this);
        server.start();
        System.out.println("Server " + name + " is listening on port " + port);
    }

    private CustomClient connectTo(int port) throws IOException {
        return new CustomClient("localhost", port);
    }

    private void executeSomewhere() throws XmlRpcException, IOException, InterruptedException {
        int number = cli.readInt("\nType client/server number");
        String result = executeOnServer(number);
        System.out.println(result);
        executeSomewhere();
    }

    private String executeOnServer(int number) throws InterruptedException, XmlRpcException, IOException {
        Vector<Integer> params = new Vector<>();
        params.add(number);
        return (String) client.executeOnServer("show", params);
    }

    public String show(int number) throws InterruptedException, XmlRpcException, IOException {
        if (number == myNumber) {
            return "this is response from server " + myNumber;
        }
        else {
            return executeOnServer(number) + ", passed by server " + myNumber;
        }
    }
}
