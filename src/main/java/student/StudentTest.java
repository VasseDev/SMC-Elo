package student;

import admin.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentTest {
    private final Subject subject;
    private final String date;
    private final int initialHour;
    private final int finalHour;
    private final Mark mark;

    public StudentTest(Subject subject, String date, Mark mark, int initialHour, int finalHour){
        this.subject=subject;
        this.date=date;
        this.initialHour = initialHour;
        this.finalHour = finalHour;
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public int getInitialHour() {
        return initialHour;
    }

    public int getFinalHour() {
        return finalHour;
    }
    public Mark getMark() {
        return mark;
    }

    public StringProperty getMarkProperty() {
        return new SimpleStringProperty(String.valueOf(mark.getValue()));
    }
}
