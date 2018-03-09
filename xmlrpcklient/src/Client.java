import org.apache.xmlrpc.AsyncCallback;
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
        executeSum(client);
        executeIsCharOnPosition(client);
        executeSort(client);
    }

    private void executeMethod(XmlRpcClient client) throws XmlRpcException, IOException {
        String serverPrefix = "myServer.";
        String method = cli.readMethod();

        Vector<Object> params = new Vector<>();
        addParams(params);

        Object result = client.execute(serverPrefix + method, params);
        System.out.println("\n" + result);
    }

    private void addParams(Vector<Object> params) {
        Object param = cli.readParams();
        if (param != null) {
            params.addElement(param);
            addParams(params);
        }
    }

    private static void executeShow(XmlRpcClient client) throws XmlRpcException, IOException {
        String result = (String) client.execute("myServer.show", new Vector<>());
        System.out.println("\n" + result);
    }

    private static void executeSum(XmlRpcClient client) throws XmlRpcException, IOException {
        Vector<Integer> params = prepareVectorOfIntegers();
        Object result = client.execute("myServer.sum", params);
        System.out.println("sum: " + result);
    }

    private static Vector<Integer> prepareVectorOfIntegers() {
        Vector<Integer> params = new Vector<>();
        params.addElement(13);
        params.addElement(21);
        return params;
    }

    private void executeIsCharOnPosition(XmlRpcClient client) throws XmlRpcException, IOException {
        Vector<Object> params = prepareVectorOfObjects();
        Object result = client.execute("myServer.isCharOnPosition", params);
        System.out.println("isCharOnPosition: " + result);
    }

    private Vector<Object> prepareVectorOfObjects() {
        Vector<Object> params = new Vector<>();
        params.add("o");
        params.add(1);
        params.add("dog");
        return params;
    }

    private static void executeSort(XmlRpcClient client) {
        Vector<Object[]> params = prepareVectorOfArrays();
        long startTime = System.currentTimeMillis();
        AsyncCallback sortCallback = new MySortAsyncCallback(startTime);
        client.executeAsync("myServer.sort", params, sortCallback);
    }

    private static Vector<Object[]> prepareVectorOfArrays() {
        Integer[] arr = prepareArray();
        Vector<Object[]> params = new Vector<>();
        params.add(arr);
        return params;
    }

    private static Integer[] prepareArray() {
        int size = 1000;
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }
}
