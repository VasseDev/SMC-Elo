package student;

import javafx.beans.property.StringProperty;

public class Mark {
    private double value;

    public Mark(double mark){
        this.value=mark;
    }

    public double getValue(){
        return value;
    }
}