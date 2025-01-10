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
import DAO.TaskDAO;
import DAO.TaskDAOImpl;
import Services.TaskSpaceService;
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
            TaskDAO taskdao = new TaskDAOImpl(conn);
            TaskSpaceDAO taskspacedao = new TaskSpaceDAOImpl(conn);
            UserService userServ = new UserService(userdao, taskspacedao);
            TaskSpaceService taskspaceServ = new TaskSpaceService(taskdao);

            userServ.populateTaskSpaces(user);
            
            
            session.setAttribute("user", user);

            if (session.getAttribute("currTaskspaceId") == null && 
                user.getTaskSpaces() != null && 
                !user.getTaskSpaces().isEmpty()) {
                session.setAttribute("currTaskspaceId", user.getTaskSpaces().get(0).getId());
            }
            
            long taskspaceId = (long)session.getAttribute("currTaskspaceId");
            TaskSpace taskspace = user.getTaskSpaceById(taskspaceId);
            taskspaceServ.populateTasks(taskspace);

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