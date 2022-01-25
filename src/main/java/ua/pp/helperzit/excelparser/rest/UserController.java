package ua.pp.helperzit.excelparser.rest;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestParam;
import ua.pp.helperzit.excelparser.repository.UserRepository;
import ua.pp.helperzit.excelparser.service.models.User;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/index")
    public String showUsersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/new")
    public String createUserPage(User user) {

        return "users/new";
    }

    @PostMapping("/new")
    public String addNewUser(@Valid User user, BindingResult result, Model model) {
        
        if(result.hasErrors()) {
            return "users/new";
        }
        
        userRepository.save(user);
        return "redirect:/users/index";
    }
    
    @GetMapping("/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        model.addAttribute("user", user);
        return "users/updatePage";
    }
    
    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            user.setId(id);
            return "users/updateUser";
        }
        
        userRepository.save(user);
        return "redirect:/users/index";
    }
    
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        userRepository.delete(user);
        return "redirect:/users/index";
    }

}
