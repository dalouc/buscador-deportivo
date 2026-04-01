import java.io.*;
import java.util.*;
import java.awt.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class list extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("GET Request. No Form Data Posted");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
    	String url="jdbc:mysql://localhost/sporting_manager";
        String type;
	    String text;
	    String order;
	    Connection con;
        Statement stmt;
	    ResultSet rs;
	    res.setContentType("text/html");
        PrintWriter out = res.getWriter();
	
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            out.println("ClassNotFoundException: ");
            out.println(e.getMessage());
        }
        try{
            con = DriverManager.getConnection (url, "root", "root");
            stmt = con.createStatement();
	        type=req.getParameter("type");
	        text=req.getParameter("text1");
	        order=req.getParameter("order");
	        if (type.equals("all_activities")){
	            rs=stmt.executeQuery("SELECT * FROM ACTIVITIES ORDER BY "+order); 
	        }
	        else if (type.equals("all_pavillions")){
				rs=stmt.executeQuery("SELECT * FROM PAVILLIONS ORDER BY "+order); 
	        }
			else if (type.equals("free_places")){
				rs=stmt.executeQuery("SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION ORDER BY "+order); 
	        }
			else if (type.equals("cost")){
				rs=stmt.executeQuery("SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION AND ACTIVITIES.COST <= '"+text+"' ORDER BY "+order); 
	        }
			else{
				rs=stmt.executeQuery("SELECT * FROM ACTIVITIES, PAVILLIONS WHERE ACTIVITIES.TOTAL_PLACES > ACTIVITIES.OCCUPIED_PLACES AND ACTIVITIES.PAVILLION_NAME = PAVILLIONS.PAVILLION AND ACTIVITIES.PAVILLION_NAME = '" +text+"' ORDER BY "+order); 
	     	}

			if (type.equals("all_pavillions")){
				out.println("<h1>List of all pavillions</h1><table>");
				out.println("<tr><b><td>PAVILLION</td><td>LOCATION</td></b></tr><p>");
			    while (rs.next()) {                    
	                String pavillion = rs.getString("PAVILLION");
					String location = rs.getString("LOCATION");
      	            out.println("<tr><td>"+pavillion+"</td>"+"<td>"+location+"</td></tr>");
	           }
	        }
            else {
                out.println("<h1>List of activities according to the searching conditions </h1><table>");
	            out.println("<tr><b><td>ID</td><td>NAME</td><td>DESCRIPTION</td><td>START_DATE</td><td>COST</td><td>PAVILLION_NAME</td><td>TOTAL_PLACES</td><td>OCCUPIED_PLACES</td></b></tr><p>");       
	            while (rs.next()) {
					int id = rs.getInt("ID");
					String name = rs.getString("NAME");
					String description = rs.getString("DESCRIPTION");
					String start_date = rs.getString("START_DATE");
					String cost = rs.getString("COST");
					String pavillion_name= rs.getString("PAVILLION_NAME");
					String total_places= rs.getString("TOTAL_PLACES");
					String occupied_places= rs.getString("OCCUPIED_PLACES");
    	            out.println("<tr><td>"+id+"</td>"+"<td>"+name+"</td>"+"<td>"+description+"</td>"+"<td>"+start_date+"</td>"+"<td>"+cost+"</td>"+"<td>"+pavillion_name+"</td>"+"<td>"+total_places+"</td>"+"<td>"+occupied_places+"</td></tr>");
	            }
			}
	        out.println("</table>");
			stmt.close();
	        con.close();
	    }  //try end
	    catch (Exception e){
	        System.err.println(e.getMessage());
	    }
	 }//doPost end
}//class end