package com.learnmicroserive.borrowingservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingResponseModel {
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private String nameBook;
    private String nameEmployee;
}
