package various;

import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Vector;

class DefaultServerClient {

    private final XmlRpcClient client;
    private final String serverPrefix = "s.";

    DefaultServerClient(XmlRpcClient client) {
        this.client = client;
    }

    void executeAll() throws XmlRpcException, IOException {
        System.out.println();
        executeSum();
        executeIsCharOnPosition();
        executeSort();
    }

    private void executeSum() throws XmlRpcException, IOException {
        Vector<Integer> params = prepareVectorOfIntegers();
        Object result = client.execute(serverPrefix + "sum", params);
        System.out.println("sum: " + result);
    }

    private static Vector<Integer> prepareVectorOfIntegers() {
        Vector<Integer> params = new Vector<>();
        params.addElement(13);
        params.addElement(21);
        return params;
    }

    private void executeIsCharOnPosition() throws XmlRpcException, IOException {
        Vector<Object> params = prepareVectorOfObjects();
        Object result = client.execute(serverPrefix + "isCharOnPosition", params);
        System.out.println("isCharOnPosition: " + result);
    }

    private Vector<Object> prepareVectorOfObjects() {
        Vector<Object> params = new Vector<>();
        params.add("o");
        params.add(1);
        params.add("dog");
        return params;
    }

    private void executeSort() {
        Vector<Object[]> params = prepareVectorOfArrays();
        long startTime = System.currentTimeMillis();
        AsyncCallback sortCallback = new MySortAsyncCallback(startTime);
        client.executeAsync(serverPrefix + "sort", params, sortCallback);
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
