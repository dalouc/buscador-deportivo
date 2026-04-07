package activities.control;

import activities.model.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet that handles client authentication.
 */
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Check if the session already contains client info
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("login") != null) {
            // Session is valid and has client data -> go directly to search
            res.sendRedirect(req.getContextPath() + "/search");
            return;
        }

        // No valid session -> show login form
        RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/login.jsp");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");

        String login = InputValidator.escapeHtml(req.getParameter("login"));
        String pwd = req.getParameter("password"); // not escaped - will be hashed for comparison

        // Track failed login attempts in the session
        HttpSession session = req.getSession(true);
        Integer attempts = (Integer) session.getAttribute("loginAttempts");
        if (attempts == null) {
            attempts = 0;
        }

        DBInteraction db = null;
        try {
            db = new DBInteraction();
            boolean authenticated = db.authenticate(login, pwd);

            if (authenticated) {
                // Prevent session fixation: invalidate old session and create a new one
                session.invalidate();
                session = req.getSession(true);

                // Store ALL client info in the server-side session (NOT in cookies)
                session.setAttribute("login", login);

                // Retrieve client details for display in the user bar
                Client client = db.getClient(login);
                if (client != null) {
                    session.setAttribute("clientName", client.getName());
                    session.setAttribute("clientSurname", client.getSurname());
                }

                // Redirect to search page
                res.sendRedirect(req.getContextPath() + "/search");

            } else {
                // Failed attempt
                attempts++;
                session.setAttribute("loginAttempts", attempts);

                if (attempts >= 3) {
                    // After 3 failed attempts, invalidate session and redirect to initial page
                    session.invalidate();
                    res.sendRedirect(req.getContextPath() + "/");
                } else {
                    req.setAttribute("error", "Incorrect login or password. Attempt " + attempts + " of 3.");
                    RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/login.jsp");
                    rd.forward(req, res);
                }
            }

        } catch (Exception e) {
            req.setAttribute("error", "Authentication error: " + e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/common/error.jsp");
            rd.forward(req, res);
        } finally {
            if (db != null) {
                try { db.close(); } catch (Exception e) { /* ignore */ }
            }
        }
    }
}
