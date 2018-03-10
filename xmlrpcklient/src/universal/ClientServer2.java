package universal;

import various.ClientServer;

import java.io.IOException;

public class ClientServer2 {

    public static void main(String[] args) throws IOException {
        int myNumber = 2;
        new ClientServer(myNumber, myNumber + 1);
    }
}
