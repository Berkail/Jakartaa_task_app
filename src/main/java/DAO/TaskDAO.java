package DAO;

import java.util.List;
import DTO.Task;

public interface TaskDAO {
    Task save(Task task);
    boolean update(Task task);
    boolean delete(long taskId);
    Task getById(long taskId);
    public List<Task> getByTaskSpaceId(long taskSpaceId);
    List<Task> getAll();
	Task save(Task task, long taskSpaceId);
}