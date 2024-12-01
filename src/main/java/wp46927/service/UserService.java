package wp46927.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import wp46927.User;

@Service
public interface UserService {
	public void addUser(User user) throws Exception;
	public void deleteUser(String name) throws Exception;
	public User getUser(String name) throws Exception;
	public void updateUser(String name, User user) throws Exception;
    void deleteUserById(Long id) throws NotFoundException;

}
