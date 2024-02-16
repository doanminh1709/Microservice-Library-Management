package com.learnmicroserive.borrowingservice.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "borrowings")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Borrowing {
    @Id
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

}
