package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AdminTest {
    private final Subject subject;
    private final String date;
    private final int initialHour;
    private final int finalHour;

    public AdminTest(Subject subject, String date, int initialHour, int finalHour){
        this.subject=subject;
        this.date=date;
        this.initialHour = initialHour;
        this.finalHour = finalHour;
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
}
