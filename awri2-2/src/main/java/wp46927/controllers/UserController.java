package wp46927.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import wp46927.User;

@Controller
public class UserController {
@GetMapping("/adduser")
public String userForm(Model model) {
model.addAttribute("user", new User());
return "add-user";
}
@PostMapping("/adduser")
public String userSubmit(@ModelAttribute User user, Model model) {
model.addAttribute("user", user);
return "user-info";
}
}