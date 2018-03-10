package various;

import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Vector;

public class ClientServer extends Server {
    private static final int defaultPort = 3000;
    private final CustomClient client;
    private final int myNumber;
    private final Cli cli = new Cli();

    public ClientServer(int myNumber, int serverNumber) throws InterruptedException, IOException, XmlRpcException {
        super(defaultPort + myNumber);
        this.myNumber = myNumber;
        client = connectTo(defaultPort + serverNumber);
        executeSomewhere();
    }

    private CustomClient connectTo(int port) throws IOException {
        return new CustomClient("localhost", port);
    }

    private void executeSomewhere() throws XmlRpcException, IOException, InterruptedException {
        int number = cli.readInt("\nType client/server number");
        printDescription(number);
        String method = cli.readString("\nType method name");
        Vector<Object> params = cli.readParams();
        Object result = executeOnServer(number, method, params);
        System.out.println("\nResult of " + method + ": " + result);
        executeSomewhere();
    }

    private void printDescription(int number) throws InterruptedException, XmlRpcException, IOException {
        Object result = executeOnServer(number, "show", new Vector<>());
        System.out.println(result);
    }

    public Object executeOnServer(int destination, String method, Vector methodParams) throws InterruptedException, XmlRpcException, IOException {
        System.out.println("\nI am server " + myNumber + ", I have request to server " + destination);
        Vector<Integer> paramsFromNumber = makeParamsFromNumber(destination);
        boolean properServer = (Boolean) client.executeOnServer("hasNumber", paramsFromNumber);
        if (properServer) {
            return client.executeOnServer(method, methodParams);
        } else {
            return passFurther(destination, method, methodParams);
        }
    }

    private Vector<Integer> makeParamsFromNumber(int number) {
        Vector<Integer> params = new Vector<>();
        params.add(number);
        return params;
    }

    private Object passFurther(int destination, String method, Vector methodParams) throws InterruptedException, XmlRpcException, IOException {
        Vector<Object> params = new Vector<>();
        params.add(destination);
        params.add(method);
        params.add(methodParams);
        return client.executeOnServer("executeOnServer", params);
    }

    public boolean hasNumber(int number) {
        return myNumber == number;
    }
}
