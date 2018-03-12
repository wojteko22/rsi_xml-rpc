package various;

import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class ClientServer extends Server {
    private static final int defaultPort = 3000;
    private final CustomClient client;
    private final int myNumber;
    private final Cli cli = new Cli();

    public ClientServer(int myNumber, int serverNumber) throws IOException, XmlRpcException, InterruptedException {
        super(defaultPort + myNumber);
        this.myNumber = myNumber;
        client = new CustomClient("localhost", defaultPort + serverNumber);
        executeSomewhere();
    }

    private void executeSomewhere() throws InterruptedException, XmlRpcException, IOException {
        int destination = cli.readInt("\nType destination number");
        printResult(destination, "show", new Vector<>(), "");
        String method = cli.readString("\nType method name");
        Vector<Object> params = cli.readParams();
        if (method.equals("playTableTennis")) {
            params.add(myNumber);
            executeOnServer(destination, method, params, new Vector<>());
            executeSomewhere();
        }
        printResult(destination, method, params, "\nResult of " + method + ": ");
        executeSomewhere();
    }

    private void printResult(int destination, String method, Vector<Object> params, String text) throws InterruptedException, XmlRpcException, IOException {
        try {
            Object result = executeOnServer(destination, method, params, new Vector<>());
            System.out.println(text + result);
        } catch (Exception e) {
            System.out.println("\n" + e);
            executeSomewhere();
        }
    }

    public Object executeOnServer(int destination, String method, Vector methodParams, Vector<Integer> visited) throws InterruptedException, XmlRpcException, IOException {
        System.out.println("I am server " + myNumber + " and I pass it to server " + destination);
        if (visited.contains(myNumber)) {
            throw new RuntimeException("Such server does not belong to servers ring. Cannot invoke method");
        }
        visited.add(myNumber);
        Vector<Integer> paramsFromNumber = makeParamsFromNumber(destination);
        boolean properServer = (Boolean) client.executeOnServer("hasNumber", paramsFromNumber);
        if (properServer) {
            return client.executeOnServer(method, methodParams);
        }
        else {
            return passFurther(destination, method, methodParams, visited);
        }
    }

    private Vector<Integer> makeParamsFromNumber(int number) {
        Vector<Integer> params = new Vector<>();
        params.add(number);
        return params;
    }

    private Object passFurther(int destination, String method, Vector methodParams, Vector<Integer> visited) throws InterruptedException, XmlRpcException, IOException {
        Vector<Object> params = new Vector<>();
        params.add(destination);
        params.add(method);
        params.add(methodParams);
        params.add(visited);
        return client.executeOnServer("executeOnServer", params);
    }

    public boolean hasNumber(int number) {
        return myNumber == number;
    }

    public String playTableTennis(String commend, int source) throws InterruptedException, XmlRpcException, IOException {
        if (commend.equals("CUT_SHOT")) {
            System.exit(0);
        }
        if (new Random().nextFloat() < 0.2) {
            return response("CUT_SHOT", source);
        }
        if (commend.equals("ping")) {
            return response("pong", source);
        }
        if (commend.equals("pong")) {
            return response("ping", source);
        }
        throw new RuntimeException("This is not table tennis commend");
    }

    private String response(String response, int source) throws InterruptedException, XmlRpcException, IOException {
        Vector<Object> params = new Vector<>();
        params.add(response);
        System.out.println("\n" + response);
        params.add(myNumber);
        executeOnServer(source, "playTableTennis", params, new Vector<>());
        return response;
    }

    @Override
    public String show() {
        return super.show() + "- playTableTennis(String text) - starts the game\n";
    }
}
