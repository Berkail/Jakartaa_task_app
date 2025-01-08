package Services;

import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import DAO.UserDAO;
import DTO.User;

public class UserService {
	UserDAO userdao;
	
	public UserService(UserDAO userdao)
	{
		this.userdao = userdao;
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
	    User user = userdao.getByEmail(email);  // Assuming the method fetches user by email
	    if (user != null && BCrypt.checkpw(password, user.getPassword())) {
	        user.setLastConnDt(LocalDateTime.now());
	        userdao.update(user);
	        return user;
	    }
	    return null;  // Return null if authentication fails
	}
}
