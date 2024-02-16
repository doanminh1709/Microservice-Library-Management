package com.learnmicroserive.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String employeeId;
    private String message;
}
