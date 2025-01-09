package Services;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.mindrot.jbcrypt.BCrypt;

import DAO.TaskSpaceDAO;
import DAO.UserDAO;
import DTO.TaskSpace;
import DTO.User;

public class UserService {
	UserDAO userdao;
	TaskSpaceDAO taskspacedao;
	private HashMap<Long, TaskSpace> workspaceCache = new HashMap<>();
	
	public UserService(UserDAO userdao)
	{
		this.userdao = userdao;
	}
	
	public UserService(UserDAO userdao, TaskSpaceDAO taskspacedao)
	{
		this.userdao = userdao;
		this.taskspacedao = taskspacedao;
	}
	
	public User createUser(String username, String email, String password)
	{
		String hashedPwd = BCrypt.hashpw(password, BCrypt.gensalt());
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(hashedPwd);
		user.setCreatedDt(LocalDateTime.now());
		return userdao.save(user);
	}
	
	public User authenticateUser(String email, String password) {
	    User user = userdao.getByEmail(email);
	    if (user != null && BCrypt.checkpw(password, user.getPassword())) {
	        user.setLastConnDt(LocalDateTime.now());
	        userdao.update(user);
	        return user;
	    }
	    return null;
	}
	
	public void populateTaskSpaces(User user) {
	    if (user == null) {
	        System.out.println("The user object is null");
	        return;
	    }
	    
	    if(user.getTaskSpaces() != null)
	    {
	    	return;
	    }
	    
	    List<TaskSpace> taskSpaces = taskspacedao.getByUserId(user.getId()) ;
	    
	    workspaceCache.clear();
        
        if (taskSpaces != null) {
            for (TaskSpace taskSpace : taskSpaces) {
                workspaceCache.put(taskSpace.getId(), taskSpace);
            }
        }
        
		user.setTaskSpaces(taskSpaces);
	}
	
	public void addTaskSpace(User user, TaskSpace taskspace)
	{
		user.getTaskSpaces().add(taskspace);
	}
}