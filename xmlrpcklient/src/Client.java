import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Vector;

public class Client {
    private static Cli cli = new Cli();

    public static void main(String[] args) throws XmlRpcException {
        tryToUseServer();
    }

    private static void tryToUseServer() throws XmlRpcException {
        String url = cli.readHostname();
        int port = cli.readPort();
        try {
            useServer(url, port);
        } catch (IOException e) {
            cli.printError();
            tryToUseServer();
        }
    }

    private static void useServer(String url, int port) throws XmlRpcException, IOException {
        XmlRpcClient client = new XmlRpcClient(url, port);
        executeShow(client);
        executeSum(client);
        executeSort(client);
    }

    private static void executeShow(XmlRpcClient client) throws XmlRpcException, IOException {
        String result = (String) client.execute("myServer.show", new Vector<>());
        System.out.println("\n" + result);
    }

    private static void executeSum(XmlRpcClient client) throws XmlRpcException, IOException {
        Vector<Integer> params = prepareVectorOfIntegers();
        Object result = client.execute("myServer.sum", params);
        System.out.println("Sum: wynik to " + result);
    }

    private static Vector<Integer> prepareVectorOfIntegers() {
        Vector<Integer> params = new Vector<>();
        params.addElement(13);
        params.addElement(21);
        return params;
    }

    private static void executeSort(XmlRpcClient client) {
        Vector<Integer[]> params = prepareVectorOfArrays();
        long startTime = System.currentTimeMillis();
        AsyncCallback sortCallback = new MySortAsyncCallback(startTime);
        client.executeAsync("myServer.sort", params, sortCallback);
    }

    private static Vector<Integer[]> prepareVectorOfArrays() {
        Integer[] arr = prepareArray();
        Vector<Integer[]> params3 = new Vector<>();
        params3.add(arr);
        return params3;
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
