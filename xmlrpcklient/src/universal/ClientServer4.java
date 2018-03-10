package universal;

import various.ClientServer;

import java.io.IOException;

public class ClientServer4 {

    public static void main(String[] args) throws IOException {
        int myNumber = 4;
        new ClientServer(myNumber, myNumber + 1);
    }
}
