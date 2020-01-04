package com.batval.controller;

import com.batval.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String view(@RequestParam(value = "name", required = false, defaultValue = "anonymous")
                               String name, Model model) {
        model.addAttribute("msg", "Hello, "+name+"!");
        return "index";
    }

    @GetMapping("/view/{name}")
    public String viewParam(@PathVariable("name") String name, Model model) {
        model.addAttribute("msg", "Hello, "+name+"!");
        return "index";
    }

    @GetMapping("/raw")
    @ResponseBody
    public String raw(){
        return "Raw data";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        Collection<User> users= List.of(
          new User("Ivan","Ivanov","ivanov@gmail.com"),
          new User ("Petr", "Petrov", "petrov@gmail.com")
        );

        model.addAttribute("users",users);
        return "/users";
    }

    @GetMapping("/users/new")
    public String getSignUp(){
        return "";
    }
}
