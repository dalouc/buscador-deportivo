package activities.control;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet that handles client logout.
 */
public class LogoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Invalidate the server-side session
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Instruct the browser to delete the JSESSIONID cookie
        Cookie jsessionCookie = new Cookie("JSESSIONID", "");
        jsessionCookie.setPath(req.getContextPath());
        jsessionCookie.setMaxAge(0); // Tells the browser to remove the cookie immediately
        jsessionCookie.setHttpOnly(true);
        res.addCookie(jsessionCookie);

        // Redirect to the initial page
        res.sendRedirect(req.getContextPath() + "/");
    }
}
