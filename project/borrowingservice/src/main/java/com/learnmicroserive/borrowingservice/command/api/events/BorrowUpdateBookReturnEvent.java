package com.learnmicroserive.borrowingservice.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowUpdateBookReturnEvent {
    private String id;
    private String bookId;
    private String employeeId;
    private Date returnDate;
}
