package com.homework.bank.service;

import com.homework.bank.model.Transaction;
import com.homework.bank.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        user.setId(UUID.randomUUID().toString());
        return user;
    }


}
