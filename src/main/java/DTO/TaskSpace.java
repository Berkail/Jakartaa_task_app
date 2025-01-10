package DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaskSpace extends TaskComponent{
	private String title;
	private String description;
	private List<Task> tasks;
	
	public Task getTaskById(long taskId)
	{
		for(Task task : tasks)
		{
			if(task.getId() == taskId)
			{
				return task;
			}
		}
		return null;
	}
}
