package com.learnmicroserive.commonserive.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailBookQuery {
    private String bookId;
}
