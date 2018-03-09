import java.util.Scanner;

public class Cli {
    private Scanner scanner = new Scanner(System.in);

    public String readHostname() {
        return readString("Type hostname");
    }

    public int readPort() {
        System.out.print("Type port: ");
        return scanner.nextInt();
    }

    public String readMethod() {
        return readString("\nType method name");
    }

    public Object readParams() {
        System.out.println(
                "\nNo more params (n)\n" +
                "Read String. (s)\n" +
                "Read int. (i)");
        String choice = readString("Type proper number");
        switch (choice) {
            case "n": return null;
            case "s": return readString("Type param");
            case "i": return readInt("Type int");
            default: return readParams();
        }
    }

    private int readInt(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextInt();
    }

    private String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.next();
    }

    public void printError() {
        System.out.println("Are you sure you passed proper hostname and port? Try localhost and 3001");
    }
}
