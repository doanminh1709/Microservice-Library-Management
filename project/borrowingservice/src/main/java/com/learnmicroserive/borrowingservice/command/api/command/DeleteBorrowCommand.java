package com.learnmicroserive.borrowingservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteBorrowCommand {
    @TargetAggregateIdentifier
    private String id;
}
