import java.util.Arrays;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;

public class Server {

    public static void main(String[] args) {
        int port = 10003;
        String name = "myServer";
        WebServer server = new WebServer(port);
        server.addHandler(name, new Server());
        server.start();
        System.out.println("Serwer " + name + " nasluchuje na porcie nr " + port);
    }

    public int sum(int x, int y) {
        return x + y;
    }

    public int difference(int minuend, int subtrahend) {
        return minuend - subtrahend;
    }

    public int sort(Vector<Integer> numbers) {
        Object[] arr = numbers.toArray();
        long elapsedTime = sortAndMeasure(arr);
        System.out.println("Posortowana tablica: " + Arrays.toString(arr));
        System.out.println("Sortowanie wykonywa³o siê " + elapsedTime + " ms");
        return 0;
    }

    private long sortAndMeasure(Object[] arr) {
        long startTime = System.currentTimeMillis();
        Arrays.sort(arr);
        long stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }

    public boolean isCharOnPosition(char c, int position, String text) {
        char actualChar = text.charAt(position);
        return c == actualChar;
    }

    public String show() {
        return "int sum(int x, int y) - returns sum of two integers\n"
                + "int difference(int minuend, int subtrahend) - returns difference of two integers\n"
                + "int sort(Vector<Integer> numbers - prints sorted integers\n"
                + "boolean isCharOnPosition(char c, int position, String text) - returns true if the char is on the " +
                "given position in the text, false otherwise";
    }
}
