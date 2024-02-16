package com.learnmicroserive.borrowingservice.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowSendMessageEvent {
    private String id;
    private String employeeId;
    private String message;
}
