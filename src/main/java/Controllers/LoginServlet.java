package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import DATA.DbConn;
import DTO.User;
import Services.UserService;
import Utilities.FormValidator;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/Login", "/login.html"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    		HttpSession session = request.getSession(false);
    		
    		boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
    		if(isAuthenticated)
    		{
    			response.sendRedirect("Dashboard");
    			return;
    		}
    		getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if(!FormValidator.areNotEmpty(email, password))
		{
			request.setAttribute("error", "All fields are required!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
		}
		
		if(!FormValidator.isValidEmail(email))
		{
			request.setAttribute("error", "Email form is incorrect");
			request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
		}
		
		Connection conn = DbConn.getInstance().getConnection();
		UserDAO userdao = new UserDAOImpl(conn);
		UserService userServ = new UserService(userdao);
		
		User user = userServ.authenticateUser(email, password);
		
		if(user == null)
		{
			request.setAttribute("error", "Email or Password is incorrect");
			request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
		}
		
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			session.invalidate();
			session = request.getSession();
		}
		
		session.setAttribute("user", user);
		
		request.setAttribute("user", user);
	    response.sendRedirect("Dashboard");
	}
}
