package activities.db;

import java.sql.*;

public class Query {

    Statement stmt;

    //Method that creates an Statement object from a Connection object
	public Query(Connection con) throws SQLException {
        stmt=con.createStatement();
    }

    //Method for executing SELECT SQL sentences 
    public ResultSet doSelect(String query) throws Exception {
    ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    //Method for executing INSERT or DELETE SQL sentences
    public int doUpdate(String query) throws SQLException {
	return stmt.executeUpdate(query);
    }
 
    // Method to close an Statement object
    public void close() throws SQLException {
        stmt.close();
    }
}
