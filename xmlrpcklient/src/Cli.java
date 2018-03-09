import java.util.Scanner;

public class Cli {
    private Scanner scanner = new Scanner(System.in);

    public String readHostname() {
        System.out.print("Pass url: ");
        return scanner.next();
    }

    public int readPort() {
        System.out.print("Pass port: ");
        return scanner.nextInt();
    }

    public void printError() {
        System.out.println("Are you sure you passed proper hostname and port? Try localhost and 3001");
    }
}
