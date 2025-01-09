package Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import DAO.TaskSpaceDAOImpl;
import DAO.TaskSpaceDAO;
import DATA.DbConn;
import DTO.User;
import DTO.TaskSpace;
import Services.UserService;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        Connection conn = null;
        
        try {
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            User user = (User) session.getAttribute("user");
            
            conn = DbConn.getInstance().getConnection();
            UserDAO userdao = new UserDAOImpl(conn);
            TaskSpaceDAO taskspacedao = new TaskSpaceDAOImpl(conn);
            UserService userServ = new UserService(userdao, taskspacedao);

            userServ.populateTaskSpaces(user);
            
            for(TaskSpace ts : user.getTaskSpaces())
            {
            	System.out.println(ts.getTitle());
            }
            
            session.setAttribute("user", user);

            if (session.getAttribute("currTaskspaceId") == null && 
                user.getTaskSpaces() != null && 
                !user.getTaskSpaces().isEmpty()) {
                session.setAttribute("currTaskspaceId", user.getTaskSpaces().get(0).getId());
            }
            
            System.out.println(session.getAttribute("currTaskspaceId"));

            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}