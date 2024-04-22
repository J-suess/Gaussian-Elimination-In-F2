import java.util.ArrayList;



 /**
  * A class defining a Row, typically used as a decomposition of a
  * matrix. A collection of rows in F2 can give insight on the
  * row space in a finite field.
  * @author Joseph Suess
  */
  
public class Row {

    private ArrayList<Boolean> data;
    private int thisSolution;
    private int leadingZeroes;

    /**
     * Constructs a new Row Object based on a collection of elements in F2 as well
     * as stores the equivalent
     * solution to this Row.
     * @param data List of Boolean Elements in the finite field F2.
     * @param solution Integer representing the current solution (0 or 1).
     */
    public Row(ArrayList<Boolean> data, int solution) {
        this.data = data;
        thisSolution = solution;
        leadingZeroes = 0;
        int index = 0;
        boolean keepGoing = true;
        while (index < data.size() && keepGoing) {
            if (data.get(index) == false) {
                index++;
                leadingZeroes++;
            } else {
                keepGoing = false;
            }
        }
    }

    /**
     * Returns a Row object that is the result of each element of this row added
     * (XOR operation) to each
     * element of another row added. Assumes the Matrix is the same.
     * @param other Other row object to compare.
     * @return New Row object holding the result. Typically used to replace rows in
     *          Gaussian Elimination.
     */
    public Row xor(Row other) {
        ArrayList<Boolean> updated = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            updated.add(getElements().get(i) ^ other.getElements().get(i));
        }

        int newSol = solution() ^ other.solution();
        Row returned = new Row(updated, newSol);

        return returned;
    }

    /**
     * Gets the List of Elements in this Row. Does not include the solution.
     * @return ArrayList of Booleans representing data in the F2 field.
     */
    public ArrayList<Boolean> getElements() {
        ArrayList<Boolean> copy = new ArrayList<Boolean>(data);
        return copy;
    }

    /**
     * Gets the Amount of Lead Zeroes in this Row. Can be used in future Gaussian
     * Elimination for quicker operations.
     * @return Int representing the # of zeroes before an element is found.
     */
    public int getLeadZeroes() {
        return leadingZeroes;
    }

    /**
     * Accessor for the size of this row object. Does not include the solution.
     * @return Size Integer.
     */
    public int size() {
        return data.size();
    }

    /**
     * Accessor for the solution of this row object. Used for back-substitution in
     * Gaussian Elimination.
     * @return Solution Integer.
     */
    public int solution() {
        return thisSolution;
    }

    /**
     * Returns a String representation of this Row Object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        for (boolean data : data) {
            sb.append(data + " ");
        }
        sb.append(" |");
        return sb.toString();
    }
}
