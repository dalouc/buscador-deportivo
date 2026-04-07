package activities.model;

/**
 * Data class representing a sporting activity.
 * Reused from the database practice (gestor-deportivo).
 */
public class Activity {

    private int id;
    private String name;
    private String description;
    private String startDate;
    private float cost;
    private String pavillionName;
    private int totalPlaces;
    private int occupiedPlaces;

    public Activity(int id, String name, String description, String startDate,
                    float cost, String pavillionName, int totalPlaces, int occupiedPlaces) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.cost = cost;
        this.pavillionName = pavillionName;
        this.totalPlaces = totalPlaces;
        this.occupiedPlaces = occupiedPlaces;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public float getCost() { return cost; }
    public void setCost(float cost) { this.cost = cost; }

    public String getPavillionName() { return pavillionName; }
    public void setPavillionName(String pavillionName) { this.pavillionName = pavillionName; }

    public int getTotalPlaces() { return totalPlaces; }
    public void setTotalPlaces(int totalPlaces) { this.totalPlaces = totalPlaces; }

    public int getOccupiedPlaces() { return occupiedPlaces; }
    public void setOccupiedPlaces(int occupiedPlaces) { this.occupiedPlaces = occupiedPlaces; }

    public int getFreePlaces() { return totalPlaces - occupiedPlaces; }
}
