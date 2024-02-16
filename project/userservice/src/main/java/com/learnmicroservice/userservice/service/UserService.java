package com.learnmicroservice.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.learnmicroservice.userservice.data.User;
import com.learnmicroservice.userservice.model.UserDTO;
import com.learnmicroservice.userservice.repository.UserRepository;

import com.learnmicroservice.userservice.utils.Constant;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
public class UserService implements IUserService  {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

//    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${jwt.key}")
    public String KEY;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(UserDTO.fromDTOtoEntity(userDTO));
     }

    @Override
    public UserDTO login(String username , String password) {
        User findUser = userRepository.findUserByUsername(username);
        if (Objects.nonNull(findUser)){
            if (passwordEncoder.matches(password, findUser.getPassword())) {
                byte[] key = KEY.getBytes();
                Algorithm algorithm = Algorithm.HMAC256(key);
                String accessToken = JWT.create()
                        .withSubject(findUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + Constant.timeUp))
                        .sign(algorithm);
                String refreshToken = JWT.create().withSubject(findUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + Constant.expressedTime))
                        .sign(algorithm);
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(findUser, userDTO);
                userDTO.setToken(accessToken);
                userDTO.setRefreshToken(refreshToken);
                return userDTO;
            }else{
//                logger.error("Password is not valid");
                System.out.println("Password is not valid");
            }
        }else{
//            logger.error("Username is not valid");
            System.out.println("Username is not valid");
        }
        return null;
    }
}
