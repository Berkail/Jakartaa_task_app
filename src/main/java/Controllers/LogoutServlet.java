package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class TaskServletAction
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
       
    }

    public LogoutServlet() {
        super();
    }
    // Handle POST request to create a new task
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session
        HttpSession session = request.getSession(false);  // false means don't create a new session if it doesn't exist
        // If the session exists, invalidate it
        if (session != null) {
            session.invalidate();  // Invalidates the session and removes all session attributes
        }
        // Redirect the user to the login page (or another page)
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

}
