package com.learnmicroserive.borrowingservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookReturnedCommand {
    @TargetAggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date returnDate;
}
