package activities.control;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet mapped to /manager that acts as an entry-point dispatcher.
 */
public class ManagerRedirectServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);

        if (session != null && "manager".equals(session.getAttribute("role"))) {
            // Manager is authenticated -> dashboard
            res.sendRedirect(req.getContextPath() + "/manager/dashboard");
        } else {
            // Not authenticated -> login page
            res.sendRedirect(req.getContextPath() + "/manager/login");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        // POST to /manager behaves the same as GET (redirect)
        doGet(req, res);
    }
}
