package Services;

import java.time.LocalDateTime;
import java.util.List;

import DAO.TaskDAO;
import DAO.TaskSpaceDAO;
import DTO.Task;
import DTO.TaskSpace;
import DTO.User;

public class TaskSpaceService {
	TaskSpaceDAO taskspacedao;
	TaskDAO taskdao;
	
	public TaskSpaceService(TaskSpaceDAO taskspacedao, TaskDAO taskdao)
	{
		this.taskspacedao = taskspacedao;
		this.taskdao = taskdao;
	}
	
	public TaskSpaceService(TaskDAO taskdao)
	{
		this.taskdao = taskdao;
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

	public void populateTasks(TaskSpace taskspace) {
		// TODO Auto-generated method stub
		if(taskspace.getTasks() != null && !taskspace.getTasks().isEmpty())
		{
			return;
		}
		List<Task> tasks = taskdao.getByTaskSpaceId(taskspace.getId());
		taskspace.setTasks(tasks);
	}
}