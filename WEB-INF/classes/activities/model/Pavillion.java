package activities.model;

/**
 * Data class representing a pavillion.
 * Reused from the database practice (gestor-deportivo).
 */
public class Pavillion {

    private String name;
    private String location;

    public Pavillion(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
