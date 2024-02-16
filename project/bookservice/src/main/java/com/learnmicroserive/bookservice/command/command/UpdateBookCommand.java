package com.learnmicroserive.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
