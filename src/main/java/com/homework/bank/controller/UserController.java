package com.homework.bank.controller;

import com.homework.bank.model.User;
import com.homework.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/loginOrSignUp")
    public String loginOrSignUp(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("userId", userService.getOrCreate(user.getName()).getId());

        return "redirect:/transactions";
    }
}
