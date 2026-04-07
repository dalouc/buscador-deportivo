package activities.control;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet filter that restricts access to the search application.
 */
public class AuthFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // Check if the user has a valid session with a login attribute
        if (session != null && session.getAttribute("login") != null) {
            // User is authenticated -> allow the request to proceed
            chain.doFilter(request, response);
        } else {
            // Not authenticated -> redirect to the initial page
            res.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
