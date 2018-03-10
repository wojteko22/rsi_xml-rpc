package universal;

import various.ClientServer;

import java.io.IOException;

public class ClientServer5 {

    public static void main(String[] args) throws IOException {
        int myNumber = 5;
        new ClientServer(myNumber, myNumber - 4);
    }
}
