import java.util.Scanner;

public class Cli {
    private Scanner scanner = new Scanner(System.in);

    public String readHostname() {
        return readString("Pass hostname");
    }

    public int readPort() {
        System.out.print("Pass port: ");
        return scanner.nextInt();
    }

    public String readMethod() {
        return readString("Pass method name");
    }

    private String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.next();
    }

    public void printError() {
        System.out.println("Are you sure you passed proper hostname and port? Try localhost and 3001");
    }
}
