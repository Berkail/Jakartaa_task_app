package DAO;

import java.util.List;
import DTO.Task;

public interface TaskDAO {
    Task save(Task task);
    Task update(Task task);
    void delete(long taskId);
    Task getById(long taskId);
    List<Task> getAll();
}