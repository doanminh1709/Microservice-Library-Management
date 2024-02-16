package com.learnmicroserive.commonserive.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollBackStatusBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;
}
