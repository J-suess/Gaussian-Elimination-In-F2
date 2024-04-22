import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Helper {
    public static void main(String[] args) {

        System.out.println("This program accepts a txt file representing a system of linear equations " +
                "and performs Gaussian Elimination in the F2 space.\nDesigned by Joseph Suess, uteid: jls22363.\n\n");

        String fileName = "C:/Users/Joe/Documents/sampleF2-5.txt";
        File file = new File(fileName);

        try {
            Scanner scan = new Scanner(file);
            help (scan);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            e.printStackTrace();
        }

    }

    private static void help(Scanner input) {
        while (input.hasNextLine()) {
            Scanner lineScanner = new Scanner(input.nextLine());
            String equation = lineScanner.next();
            
            for (int i = 0; i < equation.length(); i++) {
                if (i != equation.length() - 1) {
                    System.out.print(equation.charAt(i) + ", ");
                } else { 
                    System.out.print(equation.charAt(i) );
                }
                
            }
            
            System.out.println(";");
         
            
            // Further processing of the integer can be added here
        }
    }
}
