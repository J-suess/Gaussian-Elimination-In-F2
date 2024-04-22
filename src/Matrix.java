import java.util.ArrayList;
import java.util.Random;


 /**
  *  A class defining a Matrix Object, the pivotal object in performing
  *  row operations.
  *  @author Joseph Suess
  */
public class Matrix {

    // Defining an internal storage of Vektors
    private ArrayList<Vektor> vektors;

    // Defining an internal storage of Rows (can be thought of as Column Vektors,
    // but this is used for row operations)
    private ArrayList<Row> rows;

    // Solution Vektor for this Matrix
    private Vektor solution;

    /**
     * Constructs a new Matrix object from a series of Vektors and a solution
     * Vektor. Implicitly creates a collection of Row object also used for
     * representation.
     * @param vektorsToUse Column Vektors in A
     * @param solution Solution Vektor in B
     */
    public Matrix(ArrayList<Vektor> vektorsToUse, Vektor solution) {
        rows = new ArrayList<Row>();
        vektors = vektorsToUse;
        this.solution = solution;

        // create rows used to construct this matrix
        int size = vektors.get(0).size();
        for (int i = 0; i < size; i++) {
            ArrayList<Boolean> rowData = new ArrayList<>();
            for (Vektor temp : vektorsToUse) {
                rowData.add(temp.getElements().get(i));
            }

            rows.add(new Row(rowData, solution.getSolutions().get(i)));

        }

    }

    /**
     * Gives a String Representation of this Matrix Object.
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();
        int size = vektors.get(0).size();
        for (Row r : rows) {
            sb.append("| ");
            for (boolean element : r.getElements()) {
                int added = element ? 1 : 0;
                sb.append(added + " ");
            }

            sb.append("| ");
            sb.append(r.solution());
            sb.append(" |\n");
        }

        return sb.toString();
    }

    /**
     * Gives a String Representation of this Matrix's Rows. In F2, each row is
     * defined with 0 or 1.
     * @return String object of the span of the rows.
     */
    public String printRows() {
        StringBuilder sb = new StringBuilder();
        for (Row row : rows) {
            sb.append(row.toString() + "\n");

        }
        return sb.toString();
    }

    /**
     * Tests the linear independence of this matrix object. In other words, each
     * Vektor is linearly independent.
     * @return True if each vektor is independent, false if one vektor is not.
     */
    public boolean linearIndependence() {
        for (int i = 0; i < vektors.size() - 1; i++) {
            Vektor current = vektors.get(i);
            for (int j = i + 1; j < vektors.size(); j++) {
                Vektor compare = vektors.get(j);
                if (!current.areUnique(compare)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets this Matrix in an ideal form for Gaussian Elimiantion. Ensures there is
     * a pivot in each column for the
     * maximum amount of columns in this matrix.
     */
    public void setMatrix() {

        int rowSize = rows.size();
        int colSize = rows.get(0).size();

        for (int i = 0; i < colSize; i++) {
            for (int j = i; j < rowSize; j++) {
                for (int k = j; k < rowSize; k++)
                    if (!rows.get(j).getElements().get(i) && rows.get(k).getElements().get(i)) {
                        // Swap rows
                        Row temp = rows.get(k);
                        rows.set(k, rows.get(j));
                        rows.set(j, temp);

                    }

            }
        }

    }

    /**
     * Performs Gaussian Elimiantion on this Matrix Object by utilizing the XOR
     * attributes of each Row to clear out elements
     * Below AND Above the known pivots. Reduces to Reduced Row Echelon Form.
     */
    public void performGaussianElimination() {

        ArrayList<Row> freeColumns = new ArrayList<>();

        int rowSize = rows.get(0).size();
        int colSize = vektors.get(0).size();

        for (int i = 0; i < rowSize; i++) {

            for (int j = i; j < colSize; j++) {
                boolean currElement = rows.get(j).getElements().get(i);
                if (currElement == true) {
                    // we've found a pivot in column i!
                    // eliminating elements below using xor.
                    for (int row = j + 1; row < colSize; row++) {
                        if (rows.get(row).getElements().get(i)) {
                            // we must xor this row with the pivot!
                            Row thisRow = rows.get(j);
                            Row otherRow = rows.get(row);
                            Row updated = otherRow.xor(thisRow);
                            rows.set(row, updated);
                            // System.out.println("Current Matrix, Clearing BELOW rows in Column " + (i + 1));
                            // System.out.println(toString());
                        }
                    }

                    // eliminating elements above using xor as well!
                    for (int row = j - 1; row >= 0; row--) {
                        if (rows.get(row).getElements().get(i)) {
                            // we must xor this row with the pivot!
                            Row thisRow = rows.get(j);
                            Row otherRow = rows.get(row);
                            Row updated = otherRow.xor(thisRow);
                            rows.set(row, updated);
                            // System.out.println("Current Matrix, Clearing ABOVE rows in Column " + (i + 1));
                            // System.out.println(toString());
                        }
                    }

                    setMatrix();

                }
            }
            // System.out.println("Current Matrix, After Clearing Column " + (i + 1));
            // System.out.println(toString());

        }
    }

    /**
     * Finds a solution to this Matrix. Should be called ONLY after Gaussian
     * Elimination is completed.
     * @return Vektor Object containing the solutions to this Matrix.
     */
    public Vektor findSolutionVektor() {

        Vektor resultSolution = new Vektor(true);

        for (int i = rows.size() - 1; i >= 0; i--) {

            Row currRow = rows.get(i);
            ArrayList<Boolean> theseElements = currRow.getElements();
            if (!theseElements.contains(true) && currRow.solution() == 1) {
                System.out.println("There is no solution to the system. This is because row " + (i + 1) + ": \n"
                        + currRow.toString() + " "
                        + currRow.solution() + " \nhas no solution, as 0 elements cannot imply a true value!\n ");
                return null;
            }
        }

        if (rows.size() == rows.get(0).size()) {
            System.out.println("Your Matrix is Square, so Solutions are Easy to Find :)))");
            for (Row r : rows) {
                resultSolution.add(r.solution());
            }
        } else if (rows.size() > rows.get(0).size()) {
            System.out.println("Your Matrix is Skinny :(");
            for (Row r : rows) {
                if (r.getElements().contains(true)) {
                    resultSolution.add(r.solution());
                }
            }
        } else {
            System.out.println("Your Matrix is Fat, so Solution is not Unique :((((");
            System.out.println("Your free variables are: ");
            for (Row r : rows) {
                resultSolution.add(r.solution());
            }

            Random random = new Random();

            int remaining = rows.get(0).size() - rows.size();
            for (int i = 1; i <= remaining; i++) {
                System.out.println("x" + (i + rows.size()) + " is a free variable");

            }

        }

        return resultSolution;
    }

}
