package various;

import org.apache.xmlrpc.WebServer;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;

public class ClientServer {
    private final CustomClient client;

    public ClientServer(int myNumber, int serverNumber) throws InterruptedException, IOException, XmlRpcException {
        int defaultPort = 3000;
        serve(defaultPort + myNumber);
        client = connectTo(defaultPort + serverNumber);
        executeShow();
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

    private void executeShow() throws XmlRpcException, IOException, InterruptedException {
        try {
            client.executeShow();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("Connection refused: connect")) {
                System.out.println("Waiting for connection");
                Thread.sleep(2000);
                executeShow();
            } else throw e;
        }
    }

    public String show() {
        return "I am server";
    }
}
