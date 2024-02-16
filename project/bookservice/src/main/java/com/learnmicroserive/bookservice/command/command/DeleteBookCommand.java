package com.learnmicroserive.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
}
