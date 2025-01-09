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
import DAO.TaskSpaceDAO;
import DAO.TaskSpaceDAOImpl;
import DATA.DbConn;
import DTO.TaskSpace;
import DTO.User;
import Services.TaskSpaceService;
import Services.UserService;

/**
 * Servlet implementation class TaskSpaceServlet
 */
@WebServlet("/TaskSpace")
public class TaskSpaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
    public void init() throws ServletException {
       
    }
	
    public TaskSpaceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
