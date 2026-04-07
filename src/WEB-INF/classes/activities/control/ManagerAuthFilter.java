package activities.control;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet filter that restricts access to the manager dashboard.
 */
public class ManagerAuthFilter implements Filter {

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

        if (session != null && "manager".equals(session.getAttribute("role"))) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/manager/login");
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
