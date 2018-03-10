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
        executeDifference();
        executeIsSoLong();
        executeSort();
    }

    private void executeSum() throws XmlRpcException, IOException, InterruptedException {
        String method = "sum";
        executeOnTwoIntegers(method);
    }

    private void executeDifference() throws XmlRpcException, IOException, InterruptedException {
        String method = "difference";
        executeOnTwoIntegers(method);
    }

    private void executeOnTwoIntegers(String method) throws XmlRpcException, IOException, InterruptedException {
        Vector<Integer> params = prepareVectorOfTwoIntegers();
        printResult(method, params);
    }

    private static Vector<Integer> prepareVectorOfTwoIntegers() {
        Vector<Integer> params = new Vector<>();
        params.addElement(3);
        params.addElement(2);
        return params;
    }

    private void executeIsSoLong() throws XmlRpcException, IOException, InterruptedException {
        String method = "isSoLong";
        Vector<Object> params = prepareVectorOfObjects();
        printResult(method, params);
    }

    private Vector<Object> prepareVectorOfObjects() {
        Vector<Object> params = new Vector<>();
        params.add(3);
        params.add("dog");
        return params;
    }

    private void printResult(String method, Vector params) throws XmlRpcException, IOException, InterruptedException {
        Object result = client.executeOnServer(method, params);
        System.out.println(method + ": " + result);
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
