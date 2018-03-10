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
        Object result = executeOnServer(number, "show", new Vector<>());
        System.out.println(result);
        executeSomewhere();
    }

    public Object executeOnServer(int number, String method, Vector methodParams) throws InterruptedException, XmlRpcException, IOException {
        System.out.println("\nI am server " + myNumber + ", I have request to server " + number + "\n");
        Vector<Integer> paramsFromNumber = makeParamsFromNumber(number);
        boolean properServer = (Boolean) client.executeOnServer("hasNumber", paramsFromNumber);
        if (properServer) {
            return client.executeOnServer(method, methodParams);
        } else {
            return passFurther(number, method, methodParams);
        }
    }

    private Vector<Integer> makeParamsFromNumber(int number) {
        Vector<Integer> params = new Vector<>();
        params.add(number);
        return params;
    }

    private Object passFurther(int number, String method, Vector methodParams) throws InterruptedException, XmlRpcException, IOException {
        Vector<Object> params = new Vector<>();
        params.add(number);
        params.add(method);
        params.add(methodParams);
        return client.executeOnServer("executeOnServer", params);
    }

    public boolean hasNumber(int number) {
        return myNumber == number;
    }

    public String show() {
        return "I am server " + myNumber + ". Here are my methods:\n"
                + "int sum(int x, int y) - returns sum of two integers\n"
                + "int difference(int minuend, int subtrahend) - returns difference of two integers\n"
                + "int sort(Vector<Integer> numbers - prints sorted integers\n"
                + "boolean isCharOnPosition(char c, int position, String text) - returns true if the char is on the " +
                "given position in the text, false otherwise";
    }
}
