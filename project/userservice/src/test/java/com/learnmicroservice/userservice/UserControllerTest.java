package com.learnmicroservice.userservice;

import com.learnmicroservice.userservice.controller.UserController;
import com.learnmicroservice.userservice.data.User;
import com.learnmicroservice.userservice.model.UserDTO;
import com.learnmicroservice.userservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class UserControllerTest {

    //@ExtendWith(SpringExtension.class) : Notification for JUnit , we want to use extension is provided by Spring
    // to manage elements relevant to Spring in process testing

    //@InjectMocks : Mock class need to test

    //@BeforeEach : After test , need to init data
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp(){
        user = new User(1L ,"doanminh" ,"12345", "NV1");
        userDTO = new UserDTO(1L ,"doanminh" ,"12345", "NV1","access_token" , "refresh_token");
        ReflectionTestUtils.setField(userController , "userService" , userService);
    }

    @Test
    void getAllUserTest(){
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getAllUser()).thenReturn(users);
        //Compare expected result and returned result
        Assertions.assertEquals(users , userController.getAllUser());
    }

    @Test
    void addUserTest(){
        when(userService.addUser(userDTO)).thenReturn(user);
        Assertions.assertEquals(user , userController.addUser(userDTO));
    }

    @Test
    void login(){
        when(userService.login(anyString() , anyString())).thenReturn(userDTO);
        Assertions.assertEquals(userDTO , userController.login(userDTO));
    }
}
