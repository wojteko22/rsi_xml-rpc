import java.io.IOException;
import java.util.Vector;

import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

public class Client {

    public static void main(String[] args) throws IOException, XmlRpcException {
        XmlRpcClient client = new XmlRpcClient("http://localhost:10003");
        executeSum(client);
        executeSort(client);
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
