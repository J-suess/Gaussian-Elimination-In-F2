import java.util.ArrayList;



 /**
  * A class defining a Vektor Object in the finite field F2.
  * @author Joseph Suess
  */
public class Vektor {

    // internal storage for each vektor!
    private ArrayList<Boolean> elements;
    private ArrayList<Integer> solutions;
    private boolean isSolution;

    /**
     * Constructs a new Vektor that WILL NOT carry solutions in the finite field F2.
     * Can be thought of as a subspace of the matrix A, where A is a coefficent
     * matrix of booleans in F2.
     */
    public Vektor() {
        elements = new ArrayList<Boolean>();

    }

    /**
     * Constructs a new Vektor that WILL carry solutions in the finite field F2.
     * Can be thought of as a subspace of the solution set b, where b satifies the
     * equation Ax = b in F2.
     * @param isSolution Set to true.
     */
    public Vektor(boolean isSolution) {
        solutions = new ArrayList<>();
        this.isSolution = isSolution;
    }

    /**
     * Gets the size of this Vektor Object.
     * @return Integer representing the size.
     */
    public int size() {
        return elements.size();
    }

    /**
     * Add an element to this Vektor. This Vektor does NOT carry solutions.
     * @param val boolean representing an element in the finite field F2.
     */
    public void add(boolean val) {
        elements.add(val);
    }

    /**
     * Add an element to this Vektor. This Vektor IS a solution Vektor.
     * @param val integer representing a solution in the finite field F2.
     */
    public void add(int val) {
        solutions.add(val);
    }

    /**
     * Returns a String Representation of This Vektor.
     */
    public String toString() {
        if (isSolution) {
            return ("Solution Vektor: " + solutions.toString());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < elements.size() - 1; i++) {
            int val = elements.get(i) == false ? 0 : 1;
            sb.append(val + ", ");
        }

        int lastVal = elements.get(elements.size() - 1) == false ? 0 : 1;
        sb.append(lastVal + "}");
        return sb.toString();
    }

    /**
     * Returns a copy of the elements contained in this Vektor Object.
     * pre: !isSolution
     * @return ArrayList representing this copy.
     */
    public ArrayList<Boolean> getElements() {
        ArrayList<Boolean> copy = new ArrayList<Boolean>(elements);
        return copy;
    }

    /**
     * Returns a copy of the solutions contained in this Vektor Object.
     * pre: isSolution
     * @return ArrayList representing this copy.
     */
    public ArrayList<Integer> getSolutions() {
        ArrayList<Integer> copy = new ArrayList<Integer>(solutions);
        return copy;
    }

    /**
     * Examines whether this Vektor and other Vektor are linearly independent. Two
     * vektors are independent if they are not unique.
     * @param other Other Vektor Object
     * @return true if independent, false if dependent.
     */
    public boolean areUnique(Vektor other) {
        ArrayList<Boolean> thisElement = getElements();
        ArrayList<Boolean> otherElement = other.getElements();

        for (int i = 0; i < thisElement.size(); i++) {
            if (thisElement.get(i) != otherElement.get(i)) {
                return true;
            }
        }

        return false;
    }

    /**
     * At this index, changes the current value to the element provided.
     * If this index is out of bounds, adds the element to the end of the Vektor.
     * @param index        The index we are adding to.
     * @param missingValue The new value we are either replacing with current or
     *                      adding.
     */
    public void setElement(int index, int missingValue) {
        if (index >= solutions.size()) {
            add(missingValue);
        } else {
            solutions.set(index, missingValue);
        }

    }

}
