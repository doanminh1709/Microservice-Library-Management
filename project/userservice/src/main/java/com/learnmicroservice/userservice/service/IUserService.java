package com.learnmicroservice.userservice.service;

import com.learnmicroservice.userservice.data.User;
import com.learnmicroservice.userservice.model.UserDTO;

import java.util.*;

public interface IUserService {
    List<User> getAllUser();
    User addUser(UserDTO user);
    UserDTO login(String username , String password);
}
