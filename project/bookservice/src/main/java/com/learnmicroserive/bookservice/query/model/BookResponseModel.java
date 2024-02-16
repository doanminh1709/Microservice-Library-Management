package com.learnmicroserive.bookservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseModel {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}

