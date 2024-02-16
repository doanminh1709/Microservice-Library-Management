package com.learnmicroserive.commonserive.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseCommonModel {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
}
