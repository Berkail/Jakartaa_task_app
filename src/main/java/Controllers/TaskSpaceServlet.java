package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import DAO.TaskDAO;
import DAO.TaskDAOImpl;
import DAO.TaskSpaceDAO;
import DAO.TaskSpaceDAOImpl;
import DATA.DbConn;
import DTO.TaskSpace;
import DTO.User;
import Services.TaskSpaceService;
import Utilities.FormValidator;
import Utilities.JsonUtil;

/**
 * Servlet implementation class TaskSpaceServlet
 */
@WebServlet("/Taskspace")
public class TaskSpaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	TaskSpaceDAO taskspacedao;
	TaskDAO taskdao;
	TaskSpaceService taskspaceServ;
       
	@Override
    public void init() throws ServletException {
       taskspacedao = new TaskSpaceDAOImpl(DbConn.getInstance().getConnection());
       taskdao = new TaskDAOImpl(DbConn.getInstance().getConnection());
       taskspaceServ = new TaskSpaceService(taskspacedao, taskdao);
    }
	
    public TaskSpaceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Check if the user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return; // Make sure no further code is executed after redirect
        }
        
        // Retrieve the user object from the session
        User user = (User) session.getAttribute("user");
        
        // Get the taskSpaceId from the request parameters
        String taskSpaceIdParam = request.getParameter("taskspaceId");
        if (taskSpaceIdParam == null) {
        	System.out.println("1");
            response.sendRedirect("errorPage.jsp"); // Redirect to an error page if taskSpaceId is missing
            return;
        }
        
        long taskSpaceId;
        try {
            taskSpaceId = Long.parseLong(taskSpaceIdParam);
        } catch (NumberFormatException e) {
        	System.out.println("2");
            response.sendRedirect("errorPage.jsp"); // Handle invalid taskSpaceId format
            return;
        }
        
        // Find the selected taskSpace by ID
        TaskSpace selectedTaskSpace = user.getTaskSpaceById(taskSpaceId);
        
        // If no matching taskSpace was found, handle the error
        if (selectedTaskSpace == null) {
        	System.out.println("3");
            response.sendRedirect("errorPage.jsp"); // Redirect to an error page or show a message
            return;
        }
        
        // Populate the tasks for the selected taskSpace
        taskspaceServ.populateTasks(selectedTaskSpace);
        
        // Set the current taskSpaceId in the session
        session.setAttribute("currTaskspaceId", taskSpaceId);
        
        // Forward the request to the appropriate JSP page (for displaying tasks)
        response.sendRedirect("Dashboard");
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
