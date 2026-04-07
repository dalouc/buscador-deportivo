package activities.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Data access layer for all database operations.
 * Reused from the database practice and adapted for the JEE web application.
 */
public class DBInteraction {

    private Connection con;
    
    public DBInteraction() throws SQLException {
        String url = "jdbc:mysql://localhost/sporting_manager";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        }
        try {
            con = DriverManager.getConnection(url, "root", "1234ABCDabcd!");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            throw ex;
        }
    }

    public void close() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    /**
     * Registers a new client. Password is hashed before storage.
     */
    public void addClient(String login, String pwd, String name, String surname,
                          String address, String phone) throws Exception {
        String hashedPwd = SecurityUtils.hashPassword(pwd);
        String sql = "INSERT INTO CLIENTS (LOGIN, PASSWD, NAME, SURNAME, ADDRESS, PHONE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, hashedPwd);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.executeUpdate();
        }
    }

    /**
     * Authenticates a client by comparing the hash of the provided password
     * with the stored hash.
     * @return true if login/password match, false otherwise
     */
    public boolean authenticate(String login, String pwd) throws Exception {
        String sql = "SELECT PASSWD FROM CLIENTS WHERE LOGIN = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString(1);
                    String inputHash = SecurityUtils.hashPassword(pwd);
                    return storedHash.equals(inputHash);
                }
            }
        }
        return false;
    }

    /**
     * Retrieves a client's information by login (password is masked).
     * @return Client object or null if not found
     */
    public Client getClient(String login) throws Exception {
        String sql = "SELECT LOGIN, NAME, SURNAME, ADDRESS, PHONE FROM CLIENTS WHERE LOGIN = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getString("LOGIN"),
                        "********",
                        rs.getString("NAME"),
                        rs.getString("SURNAME"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Checks if a login already exists in the CLIENTS table.
     */
    public boolean loginExists(String login) throws Exception {
        String sql = "SELECT COUNT(*) FROM CLIENTS WHERE LOGIN = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Executes a prepared query and builds a list of Activity objects.
     */
    private ArrayList<Activity> executeActivityQuery(PreparedStatement ps) throws Exception {
        ArrayList<Activity> data = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                data.add(new Activity(
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("START_DATE"),
                    rs.getFloat("COST"),
                    rs.getString("PAVILLION_NAME"),
                    rs.getInt("TOTAL_PLACES"),
                    rs.getInt("OCCUPIED_PLACES")
                ));
            }
        }
        return data;
    }

    /**
     * Lists all activities.
     */
    public ArrayList<Activity> listAllActivities() throws Exception {
        String sql = "SELECT * FROM ACTIVITIES";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            return executeActivityQuery(ps);
        }
    }

    /**
     * Lists all pavillions.
     */
    public ArrayList<Pavillion> listAllPavillions() throws Exception {
        ArrayList<Pavillion> data = new ArrayList<>();
        String sql = "SELECT * FROM PAVILLIONS";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(new Pavillion(
                        rs.getString("PAVILLION"),
                        rs.getString("LOCATION")
                    ));
                }
            }
        }
        return data;
    }

    /**
     * Lists activities that have free places (total > occupied).
     */
    public ArrayList<Activity> listActivitiesFreePlaces() throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE TOTAL_PLACES > OCCUPIED_PLACES";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            return executeActivityQuery(ps);
        }
    }

    /**
     * Lists activities with free places costing less than or equal to a given amount.
     */
    public ArrayList<Activity> listActivitiesByMaxCost(float maxCost) throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE TOTAL_PLACES > OCCUPIED_PLACES AND COST <= ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setFloat(1, maxCost);
            return executeActivityQuery(ps);
        }
    }

    /**
     * Lists activities with free places at a specific pavillion.
     */
    public ArrayList<Activity> listActivitiesByPavillion(String pavillionName) throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE TOTAL_PLACES > OCCUPIED_PLACES AND PAVILLION_NAME = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pavillionName);
            return executeActivityQuery(ps);
        }
    }

    /**
     * Lists activities whose name matches a given name.
     */
    public ArrayList<Activity> listActivitiesByName(String actName) throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE NAME = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, actName);
            return executeActivityQuery(ps);
        }
    }

    /**
     * Lists activities whose name or description contain a given text.
     */
    public ArrayList<Activity> listActivitiesByText(String text) throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE NAME LIKE ? OR DESCRIPTION LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String pattern = "%" + text + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            return executeActivityQuery(ps);
        }
    }

    /**
     * Adds a new activity to the database.
     */
    public void addActivity(String name, String description, String startDate,
                            float cost, String pavillionName, int totalPlaces,
                            int occupiedPlaces) throws Exception {
        String sql = "INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, startDate);
            ps.setFloat(4, cost);
            ps.setString(5, pavillionName);
            ps.setInt(6, totalPlaces);
            ps.setInt(7, occupiedPlaces);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves a single activity by its ID (used for pre-filling the edit form).
     * @return Activity object or null if not found
     */
    public Activity getActivityById(int id) throws Exception {
        String sql = "SELECT * FROM ACTIVITIES WHERE ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Activity(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("START_DATE"),
                        rs.getFloat("COST"),
                        rs.getString("PAVILLION_NAME"),
                        rs.getInt("TOTAL_PLACES"),
                        rs.getInt("OCCUPIED_PLACES")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing activity.
     */
    public void updateActivity(int id, String name, String description, String startDate,
                               float cost, String pavillionName, int totalPlaces,
                               int occupiedPlaces) throws Exception {
        String sql = "UPDATE ACTIVITIES SET NAME=?, DESCRIPTION=?, START_DATE=?, COST=?, "
                   + "PAVILLION_NAME=?, TOTAL_PLACES=?, OCCUPIED_PLACES=? WHERE ID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, startDate);
            ps.setFloat(4, cost);
            ps.setString(5, pavillionName);
            ps.setInt(6, totalPlaces);
            ps.setInt(7, occupiedPlaces);
            ps.setInt(8, id);
            ps.executeUpdate();
        }
    }
}
