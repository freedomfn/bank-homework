package com.homework.bank.service;

import com.homework.bank.common.exception.PasswordNotMatchException;
import com.homework.bank.model.Transaction;
import com.homework.bank.model.User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private static ConcurrentHashMap<String, User> users;

    static {
        users = new ConcurrentHashMap<>();
    }

    public User getOrCreate(String name, String password) {

        if (users.containsKey(name)) {
            User user = users.get(name);
            if (!user.getPassword().equals(password)) {
                throw new PasswordNotMatchException("Password or user name is incorrect");
            }
            return users.get(name);
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setId(UUID.randomUUID().toString());
        users.put(name, user);
        return user;
    }

}
