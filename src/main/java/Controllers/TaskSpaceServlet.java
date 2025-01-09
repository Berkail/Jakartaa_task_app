package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import DAO.TaskSpaceDAO;
import DAO.TaskSpaceDAOImpl;
import DATA.DbConn;
import DTO.TaskSpace;
import DTO.User;
import Services.TaskSpaceService;
import Services.UserService;
import Utilities.FormValidator;
import Utilities.JsonUtil;

/**
 * Servlet implementation class TaskSpaceServlet
 */
@WebServlet("/TaskSpace")
public class TaskSpaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	TaskSpaceDAO taskspacedao;
	TaskSpaceService taskspaceServ;
       
	@Override
    public void init() throws ServletException {
       taskspacedao = new TaskSpaceDAOImpl(DbConn.getInstance().getConnection());
       taskspaceServ = new TaskSpaceService(taskspacedao);
    }
	
    public TaskSpaceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            if (!FormValidator.areNotEmpty(title)) {
                // Return error JSON
                out.print("{\"error\": \"Title must not be null\"}");
                out.flush();
                return;
            }

            TaskSpace taskspace = taskspaceServ.createTaskSpace(title, description, user.getId());
            
            if (user.getTaskSpaces() != null) {
                user.getTaskSpaces().add(taskspace);
            }
            session.setAttribute("user", user);
            
            // Create success JSON response with the new workspace data
            String jsonResponse = String.format(
                "{\"success\": true, \"workspace\": {\"id\": %d, \"title\": \"%s\", \"description\": \"%s\"}}",
                taskspace.getId(),
                JsonUtil.escapeJsonString(title),
                JsonUtil.escapeJsonString(description)
            );
            
            out.print(jsonResponse);
            out.flush();
            
        } catch (Exception e) {
            out.print("{\"error\": \"Failed to create workspace\"}");
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
