package Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import DATA.DbConn;
import DTO.User;
import Services.UserService;
import Utilities.FormValidator;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("/Signup.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confPwd = request.getParameter("confPwd");
		
		if(!FormValidator.areNotEmpty(username, email, password, confPwd))
		{
			request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
		}
		
		if(!FormValidator.isValidEmail(email))
		{
			request.setAttribute("error", "Email form is incorrect");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
		}
		
		if(!FormValidator.doPasswordsMatch(password, confPwd))
		{
			request.setAttribute("error", "Password and Confirm Password must match");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
		}
		
		Connection conn = DbConn.getInstance().getConnection();
		
		if(conn == null)
		{
			System.out.println("Shit");
		}
		UserDAO userdao = new UserDAOImpl(conn);
		UserService userServ = new UserService(userdao);
		
		User user = userServ.createUser(username, email, password);
		
		if(user == null)
		{
			request.setAttribute("error", "Username or Email already Exists");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
		}
		
		request.setAttribute("email", email);
		request.setAttribute("password", password);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
		dispatcher.forward(request, response);
	}
}
