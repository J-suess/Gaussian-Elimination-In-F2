import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class defining a Driver for the Matrix, Vektor and Row Class. This
 * particular driver "drives" a collection of linear equations
 * in the finite field F2 and performs Gaussian Elimination step-by-step
 * to find either no solution or a potential solution depending
 * on the construction of the Matrix.
 * 
 * @author Joseph Suess
 */
public class Driver {

    /**
     * Drives the entire program!
     * 
     * @param args :)
     */
    public static void main(String[] args) {

        System.out.println("This program accepts a txt file representing a system of linear equations " +
                "and performs Gaussian Elimination in the F2 space.\nDesigned by Joseph Suess, uteid: jls22363.\n\n");

        String fileName = "C:/Users/Joe/Documents/sampleF2-7.txt";
        File file = new File(fileName);

        try {
            Scanner scan = new Scanner(file);
            performGaussianElimination(scan);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            e.printStackTrace();
        }

    }

    private static void performGaussianElimination(Scanner scan) {

        String max = scan.nextLine();
        System.out.println("You defined the maximum number of vectors for this system to be: " + max.substring(1));
        ArrayList<Vektor> vektors = new ArrayList<Vektor>();
        Vektor solutions = new Vektor(true);

        // initialize vektors
        int trueMax = Integer.parseInt(max.substring(1));
        for (int i = 1; i <= trueMax; i++) {
            Vektor toAdd = new Vektor();
            vektors.add(toAdd);
        }

        while (scan.hasNextLine()) {
            String thisLine = scan.nextLine();
            // System.out.println(thisLine);
            Scanner lineScanner = new Scanner(thisLine);
            while (lineScanner.hasNext()) {

                String thisElement = lineScanner.next();
                if (thisElement.contains("x")) {

                    // adjust for the 1-based indexing.. thanks mathematicians :(
                    // Adjust for the 1-based indexing

                    int curr;
                    if (thisElement.length() == 4) {
                        curr = Integer.parseInt(thisElement.substring(thisElement.length() - 2, thisElement.length()))
                                - 1;
                    } else {
                        curr = Integer.parseInt(thisElement.substring(thisElement.length() - 1, thisElement.length()))
                                - 1;
                    }

                    if (thisElement.substring(0, 1).equals("0")) {
                        vektors.get(curr).add(false);
                    } else {
                        vektors.get(curr).add(true);
                    }

                } else if (thisElement.equals("=")) {
                    solutions.add(lineScanner.nextInt());
                }
            }
        }
        System.out.println("Your vektors are: ");
        for (int i = 0; i < vektors.size(); i++) {
            System.out.print("v" + (i + 1) + ": " + vektors.get(i).toString());
            System.out.println();
        }

        Matrix toUse = new Matrix(vektors, solutions);
        if (toUse.linearIndependence()) {
            System.out.println("\nYour Vektors are Linearly Independent! :)\n");
        } else {
            System.out.println("\nYour Vektors are not Linearly Independent :(\n");
        }

        System.out.println("And your solutions to the vektors are: \n");
        System.out.println(solutions.toString());

        System.out.println("\nNow in matrix form: ");

        System.out.println(toUse.toString());

        // System.out.println("The Rows to perform Row Operations (xor) on are: ");
        // System.out.println(toUse.printRows());

        System.out.println("Setting up the matrix by swapping these rows: ");
        toUse.setMatrix();
        System.out.println(toUse.toString());

        System.out.println("Everything is set up! No more row exchanges will be done.\n");

        System.out.println("Here is the result of Gaussian Elimination: ");
        toUse.performGaussianElimination();
        System.out.println("Final Matrix: ");
        System.out.println(toUse.toString());

        System.out.println("Your solutions are: \n");
        Vektor solutionVektor = toUse.findSolutionVektor();
        if (solutionVektor != null) {
            System.out.println(solutionVektor.toString());
        }
    }

}
