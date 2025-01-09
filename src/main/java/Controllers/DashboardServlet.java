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
@WebServlet("/Dashboard /Dashboard.jsp")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user"); // Get user from session

		if (user == null) {
			// Redirect to login page if user is not logged in
			response.sendRedirect("login.jsp");
			return;
		}

		Connection conn = DbConn.getInstance().getConnection();
		UserDAO userdao = new UserDAOImpl(conn);
		TaskSpaceDAO taskspacedao = new TaskSpaceDAOImpl(conn);
		UserService userServ = new UserService(userdao, taskspacedao);

		userServ.populateTaskSpaces(user);
		
		session.setAttribute("user", user);
		
		if(session.getAttribute("activeWorkspaceId") == null && user.getTaskSpaces() != null && !user.getTaskSpaces().isEmpty())
		{
			session.setAttribute("activeWorkspaceId", user.getTaskSpaces().get(0).getId());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
