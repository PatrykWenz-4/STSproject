package wp46927.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wp46927.Error;
import wp46927.Result;
import wp46927.User;
import wp46927.UserAPI;
import wp46927.UserRepository;
import wp46927.service.UserService;

@RestController
public class UserRestController implements UserAPI {

    @Autowired
    private UserService userService;
    UserRepository repository;
    
    // Dodanie użytkownika
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);

            // Utworzenie i zwrócenie odpowiedzi po pomyślnym dodaniu użytkownika
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result(0, user.getName(), "User added successfully."));
        } catch (Exception e) {
            // Obsługa błędu przy nieudanym dodawaniu użytkownika
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("403", "Unable to add user."));
        }
    }

    // Usunięcie użytkownika
    public ResponseEntity<Object> deleteUser(@PathVariable String name) {
        try {
            userService.deleteUser(name);

            // Utworzenie i zwrócenie odpowiedzi po pomyślnym usunięciu użytkownika
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result(0, name, "User deleted successfully."));
        } catch (Exception e) {
            // Obsługa błędu przy nieudanym usuwaniu użytkownika
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("403", "Unable to delete user."));
        }
    }
//usueniecie by ud -------------------------------------
    



    // Pobranie informacji o użytkowniku
    public ResponseEntity<Object> getUser(@PathVariable String name) {
        try {
            User user = userService.getUser(name);

            // Sprawdzenie czy użytkownik istnieje i zwrócenie odpowiedzi odpowiednio
            if (user != null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(user);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new Error("404", "User not found."));
            }
        } catch (Exception e) {
            // Obsługa błędu przy nieudanym pobieraniu informacji o użytkowniku
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("403", "Unable to get user info."));
        }
    }

    // Aktualizacja informacji o użytkowniku
    public ResponseEntity<Object> updateUser(@PathVariable String name, @RequestBody User user) {
        try {
            userService.updateUser(name, user);

            // Utworzenie i zwrócenie odpowiedzi po pomyślnej aktualizacji informacji o użytkowniku
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result(0, name, "User updated successfully."));
        } catch (Exception e) {
            // Obsługa błędu przy nieudanej aktualizacji informacji o użytkowniku
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("403", "Unable to update user info."));
        }
    }
}
