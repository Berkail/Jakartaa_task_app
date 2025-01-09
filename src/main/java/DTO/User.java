package DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
	private long id;
	private String username;
	private String email;
	private String password;
	private LocalDateTime createdDt;
	private LocalDateTime deletedDt;
	private LocalDateTime lastConnDt;
	
	private List<TaskSpace> taskSpaces;
	
	public TaskSpace getTaskSpaceById(long taskspaceId)
	{
		for(TaskSpace ts : taskSpaces)
		{
			if(ts.getId() == taskspaceId)
			{
				return ts;
			}
		}
		return null;
	}
}
