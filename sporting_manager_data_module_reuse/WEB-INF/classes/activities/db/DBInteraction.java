package activities.db;

import activities.db.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DBInteraction {
    Query q;
    Connection con;

    // Constructor that connects to the Data Base

    public DBInteraction () throws SQLException {
	    String url="jdbc:mysql://localhost/sporting_manager";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
        try {
            System.out.println("Trying to connect...");
            con = DriverManager.getConnection (url, "root", "admin");
            System.out.println("Connected!");
		}
		catch(SQLException ex) {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
        q=new Query(con);
	}    

    //method to close the Statement and the Connection objects
    
	public void close()throws Exception{
        q.close();
		con.close();
	}

    // Method to add a new user to the CLIENTS table
    
	public void addusr(String login, String pwd, String name, String surname, String address, String phone)throws Exception{
        String addusr="INSERT INTO CLIENTS VALUES ('"+login+"','"+pwd+"','"+name+"','"+surname+"','"+address+"','"+phone+"')";
	    q.doUpdate(addusr);
	}

    // This method returns 'true' in case there exist a row in the CLIENTS table with the login and password passed as parameters
	// If there is not exist a row with such login and password, then it returns 'false'
	
	public boolean authentication(String login, String pwd)throws Exception{
		String list="SELECT * FROM CLIENTS WHERE LOGIN='"+login+"'";
		String password=null;
		ResultSet rs=q.doSelect(list); //rs will contain the row with login passed as parameter
		if (rs.next()){ //Check if the Resultset is empty
		    password = rs.getString(2);
		}
		if (password == null){
			return(false);
		}
		if(password.equals(pwd)){ // In case the password for this login in the table is the same as the one passed as parameter
			return(true);
		}
		else {
			return(false);
		}
    }
	
	//This method delete a user from the CLIENTS table. This is the user with login passed as parameter
	
	public void delusr(String login) throws Exception{
		String delusr="DELETE FROM CLIENTS WHERE LOGIN='"+login+"'";
		q.doUpdate(delusr);
	}

    //This method adds a new activity in the ACTIVITIES table with the data passed as parameters

	public void addact( String name, String description, String initial, float price, String pav_name, int total, int occ)throws Exception{
		String addactivity="INSERT INTO ACTIVITIES (NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES) VALUES ('"+name+"','"+description+"','"+initial+"','"+price+"','"+pav_name+"','"+total+"','"+occ+"')";
		q.doUpdate(addactivity);
	}
	
	//This method deletes a row in the ACTIVITIES table, that is the one that has the id passed as parameter
			   
	public void delact(int id) throws Exception{
		String delact="DELETE FROM ACTIVITIES WHERE ID='"+id+"'";
		q.doUpdate(delact);
	}

    //This method adds a new pavillion in the PAVILLIONS table with the data passed as parameters

    public void addpav(String pavname, String pavlocation) throws Exception{
		String addpavillion="INSERT INTO PAVILLIONS VALUES ('"+pavname+"','"+pavlocation+"')";
		q.doUpdate(addpavillion);
	}

	//This method deletes a pavillion from the PAVILLIONS table. The deleted pavillion is the one with pavillion name passed as parameter

	public void delpav(String pavname) throws Exception{
		String delpav="DELETE FROM PAVILLIONS WHERE PABELLON='"+pavname+"'";
		q.doUpdate(delpav);
	}

	//This method requests the execution of a SQL sentence for listing all the clients
	//and it retrieves all the information for each client, storing each client as an element
	//of an array. Each element contains an object of the type Client
		
	public ArrayList listallusr() throws Exception{
		ArrayList data = new ArrayList();
		String selection="SELECT * FROM CLIENTS";
		ResultSet rs=q.doSelect(selection);
		while (rs.next()) {                     
		   String login = rs.getString(1);
		   String password = rs.getString(2);
           String name = rs.getString(3);
		   String surname = rs.getString(4);
           String address = rs.getString(5);
		   String phone = rs.getString(6);
           data.add(new Client(login, password, name, surname, address, phone));
	    }
		return (data);
	}

	//This method requests the execution of a SQL sentence for listing activities depending on some criterion
	//This method is common to all the listing activities operations
	//and it retrieves all the information for each activity, storing each activity as an element
	//of an array. Each element contains an object of the type Activity

    public ArrayList listactivities(String selection) throws Exception{
		ArrayList data = new ArrayList();
		ResultSet rs=q.doSelect(selection);
		while (rs.next()) {                     
		    int id = rs.getInt(1);
			String name = rs.getString(2);
			String description = rs.getString(3);
			String initial = rs.getString(4);
			float cost = rs.getFloat(5);
			String pavname = rs.getString(6);
			int total = rs.getInt(7);
			int occupied = rs.getInt(8);
			data.add(new Activity (id, name, description, initial, cost, pavname, total, occupied));
		}
		return (data);
	}

	// This method conforms a SQL sentence for listing all the activities
		
	public ArrayList listallact() throws Exception{
		String selection="SELECT * FROM ACTIVITIES";
		ArrayList data = this.listactivities(selection);
		return (data);
	}

    // This method conforms a SQL sentence for listing the activities that have free places
	
	public ArrayList listactfreeplaces() throws Exception{
		String selection="SELECT * FROM ACTIVITIES WHERE ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES";
		ArrayList data = this.listactivities(selection);
		return (data);
	}

	// This method conforms a SQL sentence for listing the activities which have less cost that certain amount

	public ArrayList listactprice(Float price) throws Exception{
		String selection="SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.COST <="+price+"AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data); 
	}

	// This method conforms a SQL sentence for listing the activities that take place in a certain pavillion

	public ArrayList listactpav(String namepav) throws Exception{
		String selection="SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.PAVILLION_NAME='"+namepav+"'AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data);
 	}

	// This method conforms a SQL sentence for listing the activities that have a specific name

	public ArrayList listactname(String nameact) throws Exception{
		String selection="SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.NAME='"+nameact+"'AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data);
  	}

	// This method conforms a SQL sentence for listing the activities in which a specific client is registered

	public ArrayList listactusr(String login) throws Exception{
		String selection="SELECT ID, NAME, DESCRIPTION, START_DATE, COST, PAVILLION_NAME, TOTAL_PLACES, OCCUPIED_PLACES FROM REGISTRATIONS, ACTIVITIES, PAVILLIONS WHERE REGISTRATIONS.CLIENT_LOGIN='"+login+"' AND REGISTRATIONS.ACTIVITY_ID = ACTIVITIES.ID AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION";
		ArrayList data = this.listactivities(selection);
		return (data);
   	}

	//This method requests the execution of a SQL sentence for listing all the pavillions
	//and it retrieves all the information for each pavillion, storing each pavillion as an element
	//of an array. Each element contains an object of the type pavillion

	public ArrayList listallpav() throws Exception{
		ArrayList data = new ArrayList();
		String selection="SELECT * FROM PAVILLIONS";
		ResultSet rs=q.doSelect(selection);
		while (rs.next()) {                     
		    String name = rs.getString(1);
			String location = rs.getString(2);
			data.add(new Pavillion(name, location));
       }
	   return (data);
	}

	//This method registers a client for a specific activity

    public void regactivity(String login, String id) throws Exception{
		String regactivity="INSERT INTO REGISTRATIONS VALUES ('"+login+"','"+id+"')";
		q.doUpdate(regactivity);
	}

	//This method unregisters a client from a specific activity

	public void unregactivity(String login, String id) throws Exception{
		String unregactivity="DELETE FROM REGISTRATIONS WHERE REGISTRATIONS.CLIENT_LOGIN='"+login+"'AND REGISTRATIONS.ACTIVITY_ID="+id;
		q.doUpdate(unregactivity);
	}
}