package com.batval.util;

import com.batval.dao.UserDAO;
import com.batval.dao.impl.JdbcTemplateUserDAO;
import com.batval.model.User;
import com.batval.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userService.getUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email","","This email is already in use!");
        }
    }
}
