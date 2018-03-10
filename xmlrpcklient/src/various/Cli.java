package various;

import java.util.Scanner;
import java.util.Vector;

public class Cli {
    private Scanner scanner = new Scanner(System.in);

    private Object readParam() {
        System.out.println(
                "\nNo more params (n)\n" +
                        "Read String (s)\n" +
                        "Read int (i)\n" +
                        "Read array (a)"
        );
        String choice = readString("Type proper letter");
        return makeAChoice(choice);
    }

    private Object makeAChoice(String choice) {
        switch (choice) {
            case "n":
                return null;
            case "s":
                return readString("Type String");
            case "i":
                return readInt("Type int");
            case "a":
                return readArray();
            default:
                return readParam();
        }
    }

    String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.next();
    }

    int readInt(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextInt();
    }

    private Object readArray() {
        Vector<Object> vector = new Vector<>();
        System.out.println("\nPass array elements");
        addCustomParams(vector);
        System.out.println("\nArray is filled");
        return vector.toArray();
    }

    public void addCustomParams(Vector<Object> params) {
        Object param = readParam();
        if (param != null) {
            params.addElement(param);
            addCustomParams(params);
        }
    }
}
