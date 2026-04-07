package activities.control;

import activities.model.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet that handles manager authentication.
 */
public class ManagerLoginServlet extends HttpServlet {

    // Hardcoded manager password (as specified in the assignment)
    private static final String MANAGER_PASSWORD = "manager";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Check if manager is already authenticated
        HttpSession session = req.getSession(false);
        if (session != null && "manager".equals(session.getAttribute("role"))) {
            res.sendRedirect(req.getContextPath() + "/manager/dashboard");
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("/jsp/manager/login.jsp");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        String password = req.getParameter("password");

        if (MANAGER_PASSWORD.equals(password)) {
            // Create manager session
            HttpSession session = req.getSession(true);
            session.setAttribute("role", "manager");
            res.sendRedirect(req.getContextPath() + "/manager/dashboard");
        } else {
            req.setAttribute("error", "Incorrect manager password.");
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/manager/login.jsp");
            rd.forward(req, res);
        }
    }
}
