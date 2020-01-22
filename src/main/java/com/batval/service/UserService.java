package com.batval.service;

import com.batval.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserByEmail(String email);

    void add(User user);
}
