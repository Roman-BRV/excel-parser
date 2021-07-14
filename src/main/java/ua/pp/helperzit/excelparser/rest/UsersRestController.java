package ua.pp.helperzit.excelparser.rest;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import ua.pp.helperzit.excelparser.repository.UserRepository;
import ua.pp.helperzit.excelparser.service.models.User;

@Controller
@RequestMapping("/users")
public class UsersRestController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/addUser")
    public String createUserPage(User user) {    

        return "users/addUser";
    }
    
  //public ModelAndView createUser(@RequestParam("userName") String userName, ModelAndView modelAndView) {
//    @GetMapping("/create")
//    public String createUser(@RequestParam("userName") String userName, Model model) {    
//        //modelAndView.setViewName("users/urser_success");
//        model.addAttribute("userName", userName);
//        //return modelAndView;
//        return "users/urserSuccess";
//    }
    @PostMapping("/addUser")
    public String addNewUser(@Valid User user, BindingResult result, Model model) {
        
        if(result.hasErrors()) {
            return "users/addUser";
        }
        
        userRepository.save(user);
        return "redirect:/users/index";
    }
    
    @GetMapping("/index")
    public String showUsersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        model.addAttribute("user", user);
        return "users/updateUser";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            user.setId(id);
            return "users/updateUser";
        }
        
        userRepository.save(user);
        return "redirect:/users/index";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        userRepository.delete(user);
        return "redirect:/users/index";
    }

}
