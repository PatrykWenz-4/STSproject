package wp46927.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import wp46927.User;
import wp46927.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    // Dodawanie użytkownika
    public void addUser(User user) throws Exception {
        repository.save(user);
    }
    



    
    @Override
    @Transactional
    // Usuwanie użytkownika
    public void deleteUser(String name) throws Exception {
        User user = repository.findUserByName(name);

        if (user == null) {
            throw new NotFoundException();
        }

        repository.deleteByName(name);
    }

    // Pobieranie informacji o użytkowniku
    public User getUser(String name) throws Exception {
        try {
            return repository.findUserByName(name);
        } catch (Exception e) {
            throw e;
        }
    }

    //usuwaniebyID----------------
    @Override
    @Transactional
    public void deleteUserById(Long id) throws NotFoundException {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            System.out.println("User not found with ID: " + id);

        }

        repository.deleteById(id);

        System.out.println("User deleted successfully with ID: " + id);
    }
    
    
    
    // Aktualizacja informacji o użytkowniku
    public void updateUser(@Header("username") String name, @Payload User user) throws Exception {
        User dbUser = repository.findUserByName(name);

        if (dbUser != null) {
            if (user.getName() != null) {
                dbUser.setName(user.getName());
            }

            if (user.getEmail() != null) {
                dbUser.setEmail(user.getEmail());
            }
        }

        repository.save(dbUser);
    }




}



