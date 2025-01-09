package Services;

import java.time.LocalDateTime;
import java.util.List;

import DAO.TaskSpaceDAO;
import DTO.TaskSpace;
import DTO.User;

public class TaskSpaceService {
	TaskSpaceDAO taskspacedao;
	
	public TaskSpaceService(TaskSpaceDAO taskspacedao)
	{
		this.taskspacedao = taskspacedao;
	}
	
	public TaskSpace createTaskSpace(String title, String description, long userId)
	{
		TaskSpace taskspace = new TaskSpace();
		taskspace.setTitle(title);
		taskspace.setDescription(description);
		taskspace.setCreatedDt(LocalDateTime.now());
		taskspace.setDeletedDt(null);
		taskspace.setLastModified(null);
		return taskspacedao.save(taskspace, userId);
	}
}