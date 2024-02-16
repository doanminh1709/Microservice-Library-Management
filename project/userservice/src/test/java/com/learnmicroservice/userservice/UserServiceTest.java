package com.learnmicroservice.userservice;

import com.learnmicroservice.userservice.data.User;
import com.learnmicroservice.userservice.model.UserDTO;
import com.learnmicroservice.userservice.repository.UserRepository;
import com.learnmicroservice.userservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void initData() {
        user = new User(1L, "doanminh", "12345", "NV1");
        userDTO = new UserDTO(1L, "doanminh", "12345","NV1", "access_token", "refresh_token");
        ReflectionTestUtils.setField(userService , "passwordEncoder" , passwordEncoder);
        ReflectionTestUtils.setField(userService , "userRepository", userRepository);
    }

    @Test
    void getAllUserTest(){
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        Assertions.assertEquals(users , userService.getAllUser());
    }

    @Test
    void saveUserTest(){
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn(userDTO.getPassword());
        when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals(user , userService.addUser(userDTO));
    }

    @Test
    void loginWithUserNonNullAndMatchPasswordTest(){
        when(userRepository.findUserByUsername(userDTO.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        Assertions.assertNotNull(userService.login(userDTO.getUsername() , userDTO.getPassword()));
    }

    @Test
    void loginWithUserNonNullAndNotMatchPasswordTest(){
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        Assertions.assertNull(userService.login(user.getUsername() , user.getPassword()));
    }

    @Test
    void loginWithUserNull(){
        when(userRepository.findUserByUsername(anyString())).thenReturn(null);
        Assertions.assertNull(userService.login(user.getUsername() , user.getPassword()));
    }
}
