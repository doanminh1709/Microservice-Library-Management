package com.learnmicroservice.userservice.repository;

import com.learnmicroservice.userservice.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {
    User findUserByUsername(String username);
}
