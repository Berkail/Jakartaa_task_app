package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DTO.Task;

public class TaskDAOImpl implements TaskDAO{
	private Connection connection;
	
	public TaskDAOImpl(Connection connection)
	{
		this.connection = connection;
	}

	@Override
	public Task save(Task task, long taskSpaceId) {
	    // SQL query for inserting a new task
	    String query = "INSERT INTO tasks (content, priority, creation_dt, task_space_id) "
	                 + "VALUES (?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        // Set parameters for the task
	        stmt.setString(1, task.getContent());  // Task content
	        stmt.setInt(2, task.getPriority());  // Task priority
	        stmt.setTimestamp(3, task.getCreatedDt() != null ? Timestamp.valueOf(task.getCreatedDt()) : null);  // Task creation timestamp
	        stmt.setLong(4, taskSpaceId);  // The task's associated taskSpaceId

	        // Execute the insert query
	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows > 0) {
	            // If the task was inserted successfully, retrieve the generated ID
	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    task.setId(generatedKeys.getLong(1));  // Set the generated task ID
	                }
	            }
	            return task;  // Return the task with the assigned ID
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Handle exceptions (e.g., log the error)
	    }

	    return null;  // Return null if the insertion failed
	}


	public boolean update(Task task) {
        String query = "UPDATE tasks SET content = ?, priority = ?, last_modified_dt = ?, completion_dt = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, task.getContent());  // Update task content
            stmt.setInt(2, task.getPriority());    // Update task priority
            stmt.setTimestamp(3, task.getLastModified() != null ? Timestamp.valueOf(task.getLastModified()) : null);
            stmt.setTimestamp(4, task.getCompletedDt() != null ? Timestamp.valueOf(task.getCompletedDt()) : null);
            stmt.setLong(5, task.getId());  // Specify the task to update by its ID

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return true;  // Return the updated task
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return null if update failed
    }


	@Override
    public boolean delete(long taskId) {
        String query = "UPDATE tasks SET deletion_dt = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(2, taskId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Task deleted (soft delete) with ID: " + taskId);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public Task getById(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public List<Task> getByTaskSpaceId(long taskSpaceId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE task_space_id = ? And completion_dt IS NULL ";  // Assuming tasks table has task_space_id column
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, taskSpaceId);  // Set the taskSpaceId parameter
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Map result set to Task object
                    Task task = new Task();
                    task.setId(rs.getLong("id"));  // Assuming Task has an id column
                    task.setContent(rs.getString("content"));  // Assuming Task has a content column
                    task.setPriority(rs.getInt("priority"));  // Assuming Task has a priority column
                    task.setCreatedDt(rs.getTimestamp("creation_dt") != null ? rs.getTimestamp("creation_dt").toLocalDateTime() : null);
                    task.setDeletedDt(rs.getTimestamp("deletion_dt") != null ? rs.getTimestamp("deletion_dt").toLocalDateTime() : null);
                    task.setLastModified(rs.getTimestamp("last_modified_dt") != null ? rs.getTimestamp("last_modified_dt").toLocalDateTime() : null);
                    task.setCompletedDt(rs.getTimestamp("completion_dt") != null ? rs.getTimestamp("completion_dt").toLocalDateTime() : null);
                    tasks.add(task);  // Add Task to the list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions appropriately (e.g., log them)
        }
        
        return tasks;  // Return the list of tasks
    }

	@Override
	public List<Task> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task save(Task task) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
