package Services;

import java.time.LocalDateTime;

import DAO.TaskDAO;
import DTO.Task;

public class TaskService {
	TaskDAO taskdao;
		
	public TaskService(TaskDAO taskdao)
	{
		this.taskdao = taskdao;
	}

	public Task createTask(String content, int priority, long taskspaceId) {
		// TODO Auto-generated method stub
		Task task = new Task();
		task.setContent(content);
		task.setPriority(priority);
		task.setCreatedDt(LocalDateTime.now());
		return taskdao.save(task, taskspaceId);
	}

}
