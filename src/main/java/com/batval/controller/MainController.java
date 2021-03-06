package com.batval.controller;

import com.batval.model.User;
import com.batval.service.UserService;
import com.batval.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class MainController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    //List<User> users = new ArrayList<>();

    @GetMapping("/")
    public String view(@RequestParam(value = "name", required = false, defaultValue = "anonymous")
                               String name, Model model) {
        model.addAttribute("msg", "Hello, " + name + "!");
        return "index";
    }

    @GetMapping("/view/{name}")
    public String viewParam(@PathVariable("name") String name, Model model) {
        model.addAttribute("msg", "Hello, " + name + "!");
        return "/index";
    }

    @GetMapping("/raw")
    @ResponseBody
    public String raw() {
        return "Raw data";
    }

    @GetMapping("/users")
    public String getUsers(Model model)  {
        model.addAttribute("users", userService.getAll());
        return "/users";
    }

    @GetMapping("/users/new")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "/sign_up";
    }

    /*
        @PostMapping("/users/new")
        public String signUp(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("email") String email) {
            users.add(new User(name, surname, email));
            return "redirect:/users";
        }
        */
    @PostMapping("/users/new")
    public String signUp(@ModelAttribute @Valid User user, BindingResult result) {
          userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "/sign_up";
        }

        userService.add(user);
        return "redirect:/users";
    }
}
