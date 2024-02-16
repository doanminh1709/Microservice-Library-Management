package com.learnmicroserive.commonserive.command;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@NoArgsConstructor
@Data
public class UpdateStatusBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;
    public UpdateStatusBookCommand(String bookId, Boolean isReady, String employeeId, String borrowId) {
        super();
        this.bookId = bookId;
        this.isReady = isReady;
        this.employeeId = employeeId;
        this.borrowId = borrowId;
    }
}
