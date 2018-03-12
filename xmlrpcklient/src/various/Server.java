package various;

import java.util.Arrays;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;

public class Server {
    private final int port;

    Server(int port) {
        this.port = port;
        serve(port);
    }

    private void serve(int port) {
        String name = "s";
        WebServer server = new WebServer(port);
        server.addHandler(name, this);
        server.start();
        System.out.println("Server " + name + " is listening on port " + port);
    }

    public int sum(int x, int y) {
        return x + y;
    }

    public int difference(int minuend, int subtrahend) {
        return minuend - subtrahend;
    }

    public String sort(Vector<Integer> numbers) {
        Object[] arr = numbers.toArray();
        long elapsedTime = sortAndMeasure(arr);
        System.out.println("\nSorting was executing for " + elapsedTime + " ms");
        return Arrays.toString(arr);
    }

    private long sortAndMeasure(Object[] arr) {
        long startTime = System.currentTimeMillis();
        Arrays.sort(arr);
        long stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }

    public boolean isSoLong(int length, String text) {
        return text.length() == length;
    }

    public String show() {
        return "\nI am server on port " + port + ". Here are my method:\n"
                + "- int sum(int x, int y) - returns sum of two integers\n"
                + "- int difference(int minuend, int subtrahend) - returns difference of two integers\n"
                + "- int sort(Vector<Integer> numbers) - prints sorted integers\n"
                + "- isSoLong(int length, String text) - returns true, if text has length characters, false otherwise\n";
    }
}
