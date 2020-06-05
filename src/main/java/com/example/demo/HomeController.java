package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String listMessage(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        if(userService.getUser() != null) {
            model.addAttribute("user", userService.getUser().getUsername());
        }
        return "list";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user,
                                          BindingResult result, Model model){

        if(result.hasErrors()){
            user.clearPassword();
            return "registration";
        }

        userRepository.save(user);
        //userService.saveUser(user);
        model.addAttribute("message", "User Account Created");

        return "confirmation";
    }

    @GetMapping("/add")
    public String messageForm(Model model) {
        model.addAttribute("message", new Message());
        return "messageForm";
    }

    @PostMapping("/add")
    public String processMassageForm(@Valid @ModelAttribute("message") Message message,
                                     BindingResult result){
        if(result.hasErrors()){
            return "messageForm";
        }

        message.setUser(userService.getUser());
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id));
        return "updateTxtForm";
    }

    @PostMapping("/updateAdd")
    public String updateMassage(@Valid @ModelAttribute("message") Message message,
                                     BindingResult result){
        if(result.hasErrors()){
            return "messageForm";
        }

        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }

}
