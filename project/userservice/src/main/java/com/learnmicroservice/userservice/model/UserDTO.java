package com.learnmicroservice.userservice.model;

import com.learnmicroservice.userservice.data.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String employeeId;
    private String token;
    private String refreshToken;

    public static User fromDTOtoEntity(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmployeeId(userDTO.getEmployeeId());
        return user;
    }
}
