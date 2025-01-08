package DAO;

import java.util.List;
import DTO.User;

public interface UserDAO {
    User save(User user);
    boolean update(User user);
    void delete(long userId);
    User getById(long userId);
    User getByEmail(String email);
    List<User> getAll();
}
