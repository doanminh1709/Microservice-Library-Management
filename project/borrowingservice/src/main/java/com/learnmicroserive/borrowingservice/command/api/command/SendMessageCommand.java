package com.learnmicroserive.borrowingservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageCommand {
    @TargetAggregateIdentifier
    private String id;
    private String employeeId;
    private String message;
}
