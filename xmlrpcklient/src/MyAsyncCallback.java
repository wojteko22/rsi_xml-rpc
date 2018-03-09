import java.net.URL;

import org.apache.xmlrpc.AsyncCallback;

public class MyAsyncCallback implements AsyncCallback {

	@Override
	public void handleResult(Object rezultat, URL url, String metoda) {
		System.out.println("success: " + metoda);
		
	}
	
	@Override
	public void handleError(Exception e, URL url, String metoda) {
		System.out.println("error: " + metoda + ": " + e + ": " + url);
	}
}
