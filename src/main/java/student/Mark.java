package student;

/**
 * Mark is a class that represents a student's mark in a test.
 * It provides a method to get the value of the mark.
 */
public class Mark {
    private double value; // The value of the mark

    /**
     * Constructs a new Mark with the specified value.
     *
     * @param mark the value of the mark
     */
    public Mark(double mark){
        this.value=mark;
    }

    /**
     * Returns the value of the mark.
     *
     * @return the value of the mark
     */
    public double getValue(){
        return value;
    }
}