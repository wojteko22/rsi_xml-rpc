import java.util.Vector;

import org.apache.xmlrpc.AsyncCallback;
import org.apache.xmlrpc.XmlRpcClient;

public class Client {

	public static void main(String[] args) {
		try {
			XmlRpcClient srv = new XmlRpcClient("http://localhost:10003");
			Vector<Integer> params = new Vector<Integer>();
			params.addElement(new Integer(13));
			params.addElement(new Integer(21));
			Object result = srv.execute("myServer.echo", params);
			int wynik = ((Integer) result).intValue();
			System.out.println("Wynik: " + wynik);
			
			MyAsyncCallback callback = new MyAsyncCallback();
			Vector<Integer> params2 = new Vector<Integer>();
			params2.addElement(new Integer(1000));
			srv.executeAsync("myServer.asy", params2, callback);
			System.out.println("Wywolano asynchronicznie");

			int size = 1000;
			Integer[] arr = new Integer[size];
			for (int i = 0; i < size; i++) {
				arr[i] = size - i;
			}
			Vector<Integer[]> params3 = new Vector<Integer[]>();
			params3.add(arr);
			long startTime = System.currentTimeMillis();
			AsyncCallback sortCallback = new MySortAsyncCallback(startTime);
			srv.executeAsync("myServer.sort", params3, sortCallback);
		} catch (Exception exception) {
			System.err.println("Klient XML-RPC: " + exception);
		}
	}
}
