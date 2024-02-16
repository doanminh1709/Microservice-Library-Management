package com.learnmicroserive.bookservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequestModel {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
