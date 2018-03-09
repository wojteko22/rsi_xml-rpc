import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

public class Client {

    public static void main(String[] args) throws XmlRpcException {
        tryToUseServer();
    }

    private static void tryToUseServer() throws XmlRpcException {
        int port = readPort();
        try {
            useServer(port);
        } catch (IOException e) {
            System.out.println("Are you sure you passed proper port? Try 10003");
            tryToUseServer();
        }
    }

    private static int readPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Pass port: ");
        return scanner.nextInt();
    }

    private static void useServer(int port) throws XmlRpcException, IOException {
        XmlRpcClient client = new XmlRpcClient("http://localhost:" + port);
        executeShow(client);
        executeSum(client);
        executeSort(client);
    }

    private static void executeShow(XmlRpcClient client) throws XmlRpcException, IOException {
        String result = (String) client.execute("myServer.show", new Vector<>());
        System.out.println(result);
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
