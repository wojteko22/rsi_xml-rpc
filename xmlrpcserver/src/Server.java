import java.util.Arrays;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;

public class Server {

	public static void main(String[] args) {
		try {
			int port = 10003;
			String name = "myServer";
			WebServer server = new WebServer(port);
			server.addHandler(name, new Server());
			server.start();
			System.out.println("Serwer " + name + " nasluchuje na porcie nr " + port);
		} catch (Exception exception) {
			System.err.println("Serwer XML-RPC: " + exception);
		}
	}
	
	public int sum(int x, int y) {
		return x + y;
	}
	
	public int difference(int minuend, int subtrahend) {
		return minuend - subtrahend;
	}
	
	public int sort(Vector<Integer> numbers) {
		Object[] arr = numbers.toArray();
		long startTime = System.currentTimeMillis();
		Arrays.sort(arr);
		System.out.println("Posortowana tablica: " + Arrays.toString(arr));
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Po stronie serwera sortowanie wykonywa³o siê " + elapsedTime + " ms");
	    return 0;
	}
	
	public boolean isCharOnPosition(char c, int position, String text) {
		char actualChar = text.charAt(position);
		return c == actualChar;
	}
	
	public String show() {
		return "int sum(int x, int y) - zwraca sumê\n"
				+ "int difference(int minuend, int subtrahend) - zwraca ró¿nicê\n"
				+ "int sort(Vector<Integer> numbers - wypisuje posortowane liczby\n"
				+ "boolean isCharOnPosition(char c, int position, String text) - zwraca true, jeœli podany znak wystêpuje na podanej pozycji w danej tekœcie, inaczej false";
	}
}
