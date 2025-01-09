package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DTO.TaskSpace;

public class TaskSpaceDAOImpl implements TaskSpaceDAO{
	private Connection connection;

	public TaskSpaceDAOImpl(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public TaskSpace save(TaskSpace taskspace, long userId) {
	    String query = "INSERT INTO task_spaces (title, description, user_id, creation_dt, deletion_dt, last_modified_dt) VALUES (?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setString(1, taskspace.getTitle());
	        stmt.setString(2, taskspace.getDescription());
	        stmt.setLong(3, userId);
	        stmt.setTimestamp(4, Timestamp.valueOf(taskspace.getCreatedDt()));
	        stmt.setTimestamp(5, taskspace.getDeletedDt() != null ? Timestamp.valueOf(taskspace.getDeletedDt()) : null);
	        stmt.setTimestamp(6, taskspace.getLastModified() != null ? Timestamp.valueOf(taskspace.getLastModified()) : null);

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows > 0) {
	            ResultSet generatedKeys = stmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                taskspace.setId(generatedKeys.getLong(1));
	            }
	        }
	        return taskspace;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}


	@Override
	public TaskSpace update(TaskSpace taskSpace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long taskSpaceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TaskSpace getById(long taskSpaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskSpace> getByUserId(long userId) {
        List<TaskSpace> taskSpaces = new ArrayList<>();
        String query = "SELECT * FROM task_spaces WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);  // Set the userId in the prepared statement
            ResultSet rs = stmt.executeQuery();
            
            // Loop through the result set and create TaskSpace objects
            while (rs.next()) {
                TaskSpace taskSpace = new TaskSpace();
                taskSpace.setId(rs.getLong("id"));
                taskSpace.setTitle(rs.getString("title"));
                taskSpace.setDescription(rs.getString("description"));
                
                taskSpace.setCreatedDt(rs.getTimestamp("creation_dt") != null ? rs.getTimestamp("creation_dt").toLocalDateTime() : null);
                taskSpace.setDeletedDt(rs.getTimestamp("deletion_dt") != null ? rs.getTimestamp("deletion_dt").toLocalDateTime() : null);
                taskSpace.setLastModified(rs.getTimestamp("last_modified_dt") != null ? rs.getTimestamp("last_modified_dt").toLocalDateTime() : null);
                // Add the TaskSpace to the list
                taskSpaces.add(taskSpace);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions properly
        }
        
        return taskSpaces;
    }

	@Override
	public List<TaskSpace> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}