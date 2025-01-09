package DAO;

import java.util.List;
import DTO.TaskSpace;

public interface TaskSpaceDAO {
    TaskSpace save(TaskSpace taskSpace, long userId);
    TaskSpace update(TaskSpace taskSpace);
    void delete(long taskSpaceId);
    TaskSpace getById(long taskSpaceId);
    List<TaskSpace> getAll();
	List<TaskSpace> getByUserId(long userId);
}