package universal;

import various.ClientServer;

import java.io.IOException;

public class ClientServer1 {

    public static void main(String[] args) throws IOException {
        int myNumber = 1;
        new ClientServer(myNumber, myNumber + 1);
    }
}
