package com.learnmicroservice.userservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class UserserviceApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertEquals(2L , 1L+1L);
    }

}
