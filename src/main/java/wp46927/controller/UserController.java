package wp46927.controller;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import wp46927.User;
import wp46927.UserRepository;
import wp46927.service.EmailService;
import wp46927.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;  // Inject EmailService

    @Autowired
    private UserRepository repository; 
    
 // Metoda wyświetlająca widok informacji o użytkowniku
    public String userInfoView(Model model, User user) {
        byte[] file = user.getFile();
        
        if (file != null && file.length > 0) {
            String base64File = Base64.getEncoder().encodeToString(user.getFile());
            model.addAttribute("base64File", base64File);
        } else {
            model.addAttribute("base64File", "NULL");
        }

        return "user-info";
    }


    
    // Endpoint for deleting a user by ID
 // Endpoint for deleting a user by ID
    @GetMapping("/deleteuser/{userId}")  // Change the path variable to userId
    public String deleteUserId(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
        } catch (NotFoundException e) {
            // Handle NotFoundException, user not found
            e.printStackTrace(); // Log the exception or handle it according to your needs
        }

        return "redirect:/adduser";
    }

    
    
    // Formularz dodawania użytkownika
    @GetMapping("/adduser")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "adduser";
    }

    // Obsługa przesłanego formularza dodawania użytkownika
    @PostMapping("/adduser")
    public String userSubmit(@RequestParam("file") MultipartFile file,
                             @ModelAttribute @Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "adduser";
        }

        model.addAttribute("user", user);

        try {
            userService.addUser(user);

            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom("patryk141476@outlook.com"); 
                messageHelper.setTo(user.getEmail());  
                messageHelper.setSubject("Welcome to Our Platform");

                // Set the content of the email
                String emailContent = "Dear " + user.getName() + ",\n\n"
                        + "Thank you for joining our platform! We are excited to have you on board.\n\n"
                        + "Best regards,\n"
                        + "wp46927";

                messageHelper.setText(emailContent);
            };

            emailService.send(preparator);

        } catch (Exception e) {
            return "adduser";
        }

        return userInfoView(model, user);
    }

    // Wyświetlenie informacji o użytkowniku
    @GetMapping("/user-info/{username}")
    public String userInfo(@PathVariable String username, Model model) {
        User user = null;

        try {
            user = userService.getUser(username);
        } catch (Exception e) {
            return "adduser";
        }

        if (user == null) {
            return "adduser";
        }

        model.addAttribute("user", user);
        return userInfoView(model, user);
    }





    // Formularz modyfikacji danych użytkownika
    @GetMapping("/modify-user/{username}")
    public String userModForm(@PathVariable String username, Model model) {
        User user = null;

        try {
            user = userService.getUser(username);
        } catch (Exception e) {
            // Obsługa błędu
        }

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }

        return "modify-user";
    }

    // Obsługa przesłanego formularza modyfikacji danych użytkownika
    @PostMapping("/modify-user/{username}")
    public String userMod(@PathVariable String username,
                          @RequestParam("file") MultipartFile file,
                          @ModelAttribute @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "modify-user";
        }

        model.addAttribute("user", user);

        try {
            userService.updateUser(username, user);
        } catch (Exception e) {
            return "modify-user";
        }

        return userInfoView(model, user);
    }
}
