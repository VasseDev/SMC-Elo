package admin;

/**
 * Subject represents a subject for a test.
 * It contains information about the name of the subject and a multiplier that affects the calculation of Elo points.
 */
public class Subject {
    private String name; // The name of the subject
    private Integer multiplier; // The multiplier for the subject

    /**
     * Constructs a new Subject with the specified name and multiplier.
     *
     * @param name the name of the subject
     * @param multiplier the multiplier for the subject
     */
    public Subject(String name, Integer multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    /**
     * Returns the name of the subject.
     *
     * @return the name of the subject
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the subject.
     *
     * @param name the new name of the subject
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the multiplier of the subject.
     *
     * @return the multiplier of the subject
     */
    public Integer getMultiplier() {
        return multiplier;
    }

    /**
     * Sets the multiplier of the subject.
     *
     * @param multiplier the new multiplier of the subject
     */
    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }
}