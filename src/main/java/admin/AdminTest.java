package admin;

/**
 * AdminTest represents a test administered by an admin.
 * It contains information about the subject of the test, the date, and the start and end times.
 */
public class AdminTest {
    private final Subject subject; // The subject of the test
    private final String date; // The date of the test
    private final int initialHour; // The start time of the test
    private final int finalHour; // The end time of the test

    /**
     * Constructs a new AdminTest with the specified subject, date, and start and end times.
     *
     * @param subject the subject of the test
     * @param date the date of the test
     * @param initialHour the start time of the test
     * @param finalHour the end time of the test
     */
    public AdminTest(Subject subject, String date, int initialHour, int finalHour){
        this.subject=subject;
        this.date=date;
        this.initialHour = initialHour;
        this.finalHour = finalHour;
    }

    /**
     * Returns the subject of the test.
     *
     * @return the subject of the test
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns the date of the test.
     *
     * @return the date of the test
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the start time of the test.
     *
     * @return the start time of the test
     */
    public int getInitialHour() {
        return initialHour;
    }

    /**
     * Returns the end time of the test.
     *
     * @return the end time of the test
     */
    public int getFinalHour() {
        return finalHour;
    }
}