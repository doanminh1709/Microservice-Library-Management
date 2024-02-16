package com.learnmicroservice.userservice;

import com.google.gson.Gson;
import com.learnmicroservice.userservice.data.User;
import com.learnmicroservice.userservice.model.UserDTO;
import com.learnmicroservice.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT , properties = "application.properties")
@PropertySource(value = "classpath:application.properties")
class IntegrationTest {

    /*
    Integration testing : Its purpose is to check whether different element in system work together and respectable or not
    @MockBean   : Create mock annotation and register to Application Context in process testing
    @BeforeAll  : Init first in program before run test
    @BeforeEach : Init first before run test
    RestTemplate : Object help we work with RestFullAPI
     */

    @LocalServerPort
    private int port;
    private static RestTemplate restTemplate;

    private String baseUrl = "http://localhost:";

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private User user = new User();
    private UserDTO userDTO = new UserDTO();
    private final Gson gson = new Gson();

    @BeforeAll
    static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp(){
        user = new User(1L , "doanminh" , "12345" , "NV1");
        userDTO = new UserDTO(1L, "doanminh" , "12345" ,"NV1",  "access_token" , "refresh_token");
        baseUrl = baseUrl.concat(port+"").concat("/api/v1/users");
    }

    @Test
    void getALlUserTest(){
        List<User> users = new ArrayList<User>();
        users.add(user);
        userRepository.save(user);
        when(userRepository.findAll()).thenReturn(users);
        ResponseEntity<?> response = restTemplate.getForEntity(baseUrl.concat("/listUser") , List.class);
        System.out.println(gson.toJson(response.getBody()));
        System.out.println(response.getStatusCode());
        Assertions.assertEquals(gson.toJson(users) , gson.toJson(response.getBody()));
        Assertions.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

    @Test
    void addUserTest(){
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn(userDTO.getPassword());
        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<?> response = restTemplate.postForEntity(baseUrl.concat("/addUser")  , user ,  User.class);
        System.out.println(gson.toJson(response.getBody()));
        System.out.println(response.getStatusCode());
        Assertions.assertEquals(gson.toJson(user) , gson.toJson(response.getBody()));
        Assertions.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

    @Test
    void loginAccountTest(){
        when(userRepository.findUserByUsername(userDTO.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(anyString() , anyString())).thenReturn(true);
           ResponseEntity<?> response = restTemplate.postForEntity(baseUrl.concat("/login") , userDTO , UserDTO.class);
           System.out.println(gson.toJson(response.getBody()));
           System.out.println(response.getStatusCode());
           Assertions.assertNotNull(response.getBody());
           Assertions.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

}

