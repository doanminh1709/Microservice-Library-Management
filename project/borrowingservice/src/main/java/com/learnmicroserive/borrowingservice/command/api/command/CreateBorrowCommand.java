package com.learnmicroserive.borrowingservice.command.api.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;


@Data
public class CreateBorrowCommand {
    @TargetAggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;

    public CreateBorrowCommand(String bookId, String employeeId, Date borrowingDate,String id) {
        super();
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.borrowingDate = borrowingDate;
        this.id = id;
    }

}
