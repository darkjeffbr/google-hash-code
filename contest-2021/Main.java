import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] inputFiles = { "FILE_NAME" };

        for (String file : inputFiles) {
            //read input file
            Scanner scanner = new Scanner(new File(file));
            String firstLine = scanner.nextLine();

            //logic
            firstLine = firstLine + " modified";

            //write result to output file
            PrintStream out = new PrintStream( new FileOutputStream(file + ".out") );
            out.println(firstLine);
            out.close();
        }

    }
}
