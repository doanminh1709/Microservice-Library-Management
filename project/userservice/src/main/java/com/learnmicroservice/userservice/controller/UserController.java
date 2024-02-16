package com.learnmicroservice.userservice.controller;

import com.learnmicroservice.userservice.data.User;
import com.learnmicroservice.userservice.model.UserDTO;
import com.learnmicroservice.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listUser")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO.getUsername() , userDTO.getPassword());
    }
}
