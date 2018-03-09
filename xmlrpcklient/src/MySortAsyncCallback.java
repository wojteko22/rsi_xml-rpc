import java.net.URL;

import org.apache.xmlrpc.AsyncCallback;

public class MySortAsyncCallback implements AsyncCallback {
	private final long startTime;
	
	public MySortAsyncCallback(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public void handleResult(Object rezultat, URL url, String metoda) {
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Po stronie klienta sortowanie wykonywa�o si� " + elapsedTime + " ms");
	}
	
	@Override
	public void handleError(Exception e, URL url, String metoda) {
		System.out.println("MySortAsyncCallback error: " + metoda + ": " + e + ": " + url);
	}
}
