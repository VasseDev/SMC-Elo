package model;

public class Test {
    private final Subject subject;
    private final String date;
    private final int initialHour;
    private final int finalHour;

    public Test(Subject subject, String date, Mark mark, int initialHour, int finalHour){
        this.subject=subject;
        this.date=date;
        this.initialHour = initialHour;
        this.finalHour = finalHour;
    }

    public Test(Subject subject, String date, int initialHour, int finalHour){
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
