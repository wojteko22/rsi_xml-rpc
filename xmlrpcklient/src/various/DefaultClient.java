package various;

import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Vector;

class DefaultClient {

    private final CustomClient client;

    DefaultClient(CustomClient client) {
        this.client = client;
    }

    void executeAll() throws XmlRpcException, IOException, InterruptedException {
        System.out.println();
        executeSum();
        executeIsCharOnPosition();
        executeSort();
    }

    private void executeSum() throws XmlRpcException, IOException, InterruptedException {
        Vector<Integer> params = prepareVectorOfIntegers();
        Object result = client.executeOnServer( "sum", params);
        System.out.println("sum: " + result);
    }

    private static Vector<Integer> prepareVectorOfIntegers() {
        Vector<Integer> params = new Vector<>();
        params.addElement(13);
        params.addElement(21);
        return params;
    }

    private void executeIsCharOnPosition() throws XmlRpcException, IOException, InterruptedException {
        Vector<Object> params = prepareVectorOfObjects();
        Object result = client.executeOnServer("isCharOnPosition", params);
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
        client.executeAsyncOnServer("sort", params, sortCallback);
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
