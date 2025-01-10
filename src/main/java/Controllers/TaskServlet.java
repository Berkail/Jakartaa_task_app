package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.TaskDAO;
import DAO.TaskDAOImpl;
import DTO.Task;
import DTO.TaskSpace;
import DTO.User;
import Services.TaskService;
import Utilities.JsonUtil;
import DATA.DbConn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class TaskServletAction
 */
@WebServlet("/Task")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TaskService taskServ;
	TaskDAO taskdao;

	@Override
	public void init() throws ServletException {
		TaskDAO taskdao = new TaskDAOImpl(DbConn.getInstance().getConnection());
		taskServ = new TaskService(taskdao);
	}

	public TaskServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	// Handle POST request to create a new task
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		long taskspaceId = (long) session.getAttribute("currTaskspaceId");

		String content = request.getParameter("content");

		TaskSpace taskspace = user.getTaskSpaceById(taskspaceId);

		if (taskspace == null) {
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"Task space not found.\"}");
			return;
		}

		List<Task> tasks = taskspace.getTasks();

		if (tasks == null) {
			tasks = new ArrayList<>();
		}

		int priority = tasks.size(); // Set priority based on current task count
		Task task = taskServ.createTask(content, priority, taskspaceId);

		if (task == null) {
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"Failed to create task.\"}");
			return;
		}

		tasks.add(task);
		session.setAttribute("user", user);

		// Send success response
		response.setContentType("application/json");
		String jsonResponse = String.format(
				"{\"success\": true, \"task\": {\"id\": %d, \"content\": \"%s\", \"priority\": %d}}", task.getId(),
				JsonUtil.escapeJsonString(task.getContent()), task.getPriority());
		response.getWriter().write(jsonResponse);
	}

	// Handle PUT request to update an existing task
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		long taskspaceId = (long) session.getAttribute("currTaskspaceId");

		// Read the request body to get the task ID
		BufferedReader reader = request.getReader();
		StringBuilder requestBody = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			requestBody.append(line);
		}

		// Parse the task ID from the JSON request body
		String json = requestBody.toString();
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		if (jsonObject.has("taskId")) {
			long taskId = jsonObject.get("taskId").getAsLong();

			TaskSpace taskspace = user.getTaskSpaceById(taskspaceId);
			if (taskspace == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("Invalid TaskSpace ID");
				return;
			}

			Task task = taskspace.getTaskById(taskId);
			if (task == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("Task not found");
				return;
			}

			// Mark the task as completed and remove from the task space
			taskServ.completeTask(task);
			taskspace.getTasks().remove(task);

			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("Task marked as completed successfully");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Task ID not provided in request body");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
