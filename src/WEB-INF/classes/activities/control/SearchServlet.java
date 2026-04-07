package activities.control;

import activities.model.*;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet that handles all client search operations.
 */
public class SearchServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        // GET requests simply show the search form
        RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/search.jsp");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        // Retrieve and sanitize form parameters
        String type = InputValidator.escapeHtml(req.getParameter("type"));
        String text = InputValidator.escapeHtml(req.getParameter("text1"));

        DBInteraction db = null;
        try {
            db = new DBInteraction();

            if ("all_pavillions".equals(type)) {
                // List all pavillions
                ArrayList<Pavillion> data = db.listAllPavillions();
                req.setAttribute("pavillions", data);
                req.setAttribute("resultType", "pavillions");

            } else {
                // All other options return activities
                ArrayList<Activity> data;

                switch (type) {
                    case "all_activities":
                        data = db.listAllActivities();
                        break;
                    case "free_places":
                        data = db.listActivitiesFreePlaces();
                        break;
                    case "cost":
                        float maxCost = Float.parseFloat(text);
                        data = db.listActivitiesByMaxCost(maxCost);
                        break;
                    case "pavillion":
                        data = db.listActivitiesByPavillion(text);
                        break;
                    case "activity_name":
                        data = db.listActivitiesByName(text);
                        break;
                    case "text_search":
                        data = db.listActivitiesByText(text);
                        break;
                    default:
                        data = new ArrayList<>();
                }
                req.setAttribute("activities", data);
                req.setAttribute("resultType", "activities");
            }

            // Forward to the results JSP
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/results.jsp");
            rd.forward(req, res);

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid number format. Please enter a valid cost.");
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/search.jsp");
            rd.forward(req, res);
        } catch (Exception e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/common/error.jsp");
            rd.forward(req, res);
        } finally {
            if (db != null) {
                try { db.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }
}
