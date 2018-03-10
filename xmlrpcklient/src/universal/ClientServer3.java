package universal;

import various.ClientServer;

import java.io.IOException;

public class ClientServer3 {

    public static void main(String[] args) throws IOException {
        int myNumber = 3;
        new ClientServer(myNumber, myNumber + 1);
    }
}
