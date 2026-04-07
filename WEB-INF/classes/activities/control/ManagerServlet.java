package activities.control;

import activities.model.*;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet that handles manager operations.
 */
public class ManagerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Verify manager session
        HttpSession session = req.getSession(false);
        if (session == null || !"manager".equals(session.getAttribute("role"))) {
            res.sendRedirect(req.getContextPath() + "/manager/login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            action = "dashboard";
        }

        DBInteraction db = null;
        try {
            switch (action) {
                case "list":
                    db = new DBInteraction();
                    ArrayList<Activity> activities = db.listAllActivities();
                    req.setAttribute("activities", activities);
                    RequestDispatcher listRd = req.getRequestDispatcher("/jsp/manager/listActivities.jsp");
                    listRd.forward(req, res);
                    break;

                case "addForm":
                    db = new DBInteraction();
                    ArrayList<Pavillion> pavs = db.listAllPavillions();
                    req.setAttribute("pavillions", pavs);
                    RequestDispatcher addRd = req.getRequestDispatcher("/jsp/manager/addActivity.jsp");
                    addRd.forward(req, res);
                    break;

                case "editForm":
                    String idStr = req.getParameter("id");
                    if (idStr != null) {
                        db = new DBInteraction();
                        int id = Integer.parseInt(idStr);
                        Activity activity = db.getActivityById(id);
                        ArrayList<Pavillion> editPavs = db.listAllPavillions();
                        req.setAttribute("activity", activity);
                        req.setAttribute("pavillions", editPavs);
                    }
                    RequestDispatcher editRd = req.getRequestDispatcher("/jsp/manager/editActivity.jsp");
                    editRd.forward(req, res);
                    break;

                case "logout":
                    session.invalidate();
                    res.sendRedirect(req.getContextPath() + "/manager/login");
                    break;

                default:
                    // Dashboard
                    RequestDispatcher dashRd = req.getRequestDispatcher("/jsp/manager/dashboard.jsp");
                    dashRd.forward(req, res);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            RequestDispatcher errRd = req.getRequestDispatcher("/jsp/common/error.jsp");
            errRd.forward(req, res);
        } finally {
            if (db != null) {
                try { db.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Verify manager session
        HttpSession session = req.getSession(false);
        if (session == null || !"manager".equals(session.getAttribute("role"))) {
            res.sendRedirect(req.getContextPath() + "/manager/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        DBInteraction db = null;
        try {
            db = new DBInteraction();

            if ("add".equals(action)) {
                // Add a new activity
                String name        = InputValidator.escapeHtml(req.getParameter("name"));
                String description = InputValidator.escapeHtml(req.getParameter("description"));
                String startDate   = InputValidator.escapeHtml(req.getParameter("startDate"));
                float cost         = Float.parseFloat(req.getParameter("cost"));
                String pavName     = InputValidator.escapeHtml(req.getParameter("pavillionName"));
                int total          = Integer.parseInt(req.getParameter("totalPlaces"));
                int occupied       = Integer.parseInt(req.getParameter("occupiedPlaces"));

                db.addActivity(name, description, startDate, cost, pavName, total, occupied);
                req.setAttribute("message", "Activity '" + name + "' added successfully.");

                // Show the list after adding
                ArrayList<Activity> activities = db.listAllActivities();
                req.setAttribute("activities", activities);
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/manager/listActivities.jsp");
                rd.forward(req, res);

            } else if ("edit".equals(action)) {
                // Edit an existing activity
                int id             = Integer.parseInt(req.getParameter("id"));
                String name        = InputValidator.escapeHtml(req.getParameter("name"));
                String description = InputValidator.escapeHtml(req.getParameter("description"));
                String startDate   = InputValidator.escapeHtml(req.getParameter("startDate"));
                float cost         = Float.parseFloat(req.getParameter("cost"));
                String pavName     = InputValidator.escapeHtml(req.getParameter("pavillionName"));
                int total          = Integer.parseInt(req.getParameter("totalPlaces"));
                int occupied       = Integer.parseInt(req.getParameter("occupiedPlaces"));

                db.updateActivity(id, name, description, startDate, cost, pavName, total, occupied);
                req.setAttribute("message", "Activity updated successfully.");

                // Show the list after editing
                ArrayList<Activity> activities = db.listAllActivities();
                req.setAttribute("activities", activities);
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/manager/listActivities.jsp");
                rd.forward(req, res);

            } else {
                res.sendRedirect(req.getContextPath() + "/manager/dashboard");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid number format in form data.");
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/common/error.jsp");
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
