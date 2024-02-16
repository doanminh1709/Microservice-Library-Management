package com.learnmicroserive.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDeleteEvent {
    private String bookId;
}
