package com.learnmicroserive.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBookCommand {
    //Todo: Đánh dấu một trường trong một lớp Aggregate, thông báo rằng trường đó chứa giá trị định danh (identifier)
    @TargetAggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
