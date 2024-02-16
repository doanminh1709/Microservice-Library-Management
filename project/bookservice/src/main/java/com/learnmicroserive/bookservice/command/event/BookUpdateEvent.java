package com.learnmicroserive.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateEvent {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
