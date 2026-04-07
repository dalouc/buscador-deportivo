package activities.control;

import activities.model.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet that handles the client registration process.
 */
public class RegisterServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Check if there are cookies with previous registration data (for pre-filling the form)
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                switch (c.getName()) {
                    case "reg_login":    req.setAttribute("login", c.getValue()); break;
                    case "reg_name":     req.setAttribute("name", c.getValue()); break;
                    case "reg_surname":  req.setAttribute("surname", c.getValue()); break;
                    case "reg_address":  req.setAttribute("address", c.getValue()); break;
                    case "reg_phone":    req.setAttribute("phone", c.getValue()); break;
                }
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/register.jsp");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("register".equals(action)) {
            // User submitted the registration form -> store in cookies and show confirmation
            String login   = InputValidator.escapeHtml(req.getParameter("login"));
            String pwd     = req.getParameter("password"); // password not escaped (will be hashed)
            String name    = InputValidator.escapeHtml(req.getParameter("name"));
            String surname = InputValidator.escapeHtml(req.getParameter("surname"));
            String address = InputValidator.escapeHtml(req.getParameter("address"));
            String phone   = InputValidator.escapeHtml(req.getParameter("phone"));

            // Store registration data in multiple cookies
            addRegCookie(res, "reg_login", login);
            addRegCookie(res, "reg_password", pwd);
            addRegCookie(res, "reg_name", name);
            addRegCookie(res, "reg_surname", surname);
            addRegCookie(res, "reg_address", address);
            addRegCookie(res, "reg_phone", phone);

            // Pass data to the confirmation JSP
            req.setAttribute("login", login);
            req.setAttribute("name", name);
            req.setAttribute("surname", surname);
            req.setAttribute("address", address);
            req.setAttribute("phone", phone);

            RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/confirmRegister.jsp");
            rd.forward(req, res);

        } else if ("confirm".equals(action)) {
            // User confirmed or rejected
            String confirm = req.getParameter("confirm");

            if ("yes".equals(confirm)) {
                // Read data from cookies and save to database
                String login = null, pwd = null, name = null, surname = null, address = null, phone = null;
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        switch (c.getName()) {
                            case "reg_login":    login = c.getValue(); break;
                            case "reg_password": pwd = c.getValue(); break;
                            case "reg_name":     name = c.getValue(); break;
                            case "reg_surname":  surname = c.getValue(); break;
                            case "reg_address":  address = c.getValue(); break;
                            case "reg_phone":    phone = c.getValue(); break;
                        }
                    }
                }

                DBInteraction db = null;
                try {
                    db = new DBInteraction();

                    // Check if login already exists
                    if (db.loginExists(login)) {
                        req.setAttribute("error", "The login '" + login + "' is already in use. Please choose a different one.");
                        req.setAttribute("login", login);
                        req.setAttribute("name", name);
                        req.setAttribute("surname", surname);
                        req.setAttribute("address", address);
                        req.setAttribute("phone", phone);
                        RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/register.jsp");
                        rd.forward(req, res);
                        return;
                    }

                    // Save the new client (password will be hashed inside addClient)
                    db.addClient(login, pwd, name, surname, address, phone);

                    // Clear registration cookies
                    clearRegCookies(res);

                    // Show success page
                    req.setAttribute("login", login);
                    RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/registerSuccess.jsp");
                    rd.forward(req, res);

                } catch (Exception e) {
                    req.setAttribute("error", "Registration error: " + e.getMessage());
                    RequestDispatcher rd = req.getRequestDispatcher("/jsp/common/error.jsp");
                    rd.forward(req, res);
                } finally {
                    if (db != null) {
                        try { db.close(); } catch (Exception e) { /* ignore */ }
                    }
                }

            } else {
                // User said "no" -> redirect back to form (GET), cookies still have the data
                res.sendRedirect(req.getContextPath() + "/register");
            }
        }
    }

    /**
     * Creates a cookie for registration data with a 30-minute lifetime.
     */
    private void addRegCookie(HttpServletResponse res, String name, String value) {
        Cookie cookie = new Cookie(name, value != null ? value : "");
        cookie.setMaxAge(30 * 60); // 30 minutes
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    /**
     * Clears all registration cookies by setting maxAge to 0.
     */
    private void clearRegCookies(HttpServletResponse res) {
        String[] names = {"reg_login", "reg_password", "reg_name", "reg_surname", "reg_address", "reg_phone"};
        for (String name : names) {
            Cookie cookie = new Cookie(name, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }
}
