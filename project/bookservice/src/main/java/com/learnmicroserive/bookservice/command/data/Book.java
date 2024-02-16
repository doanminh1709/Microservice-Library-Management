package com.learnmicroserive.bookservice.command.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    @Id
    @Column(name = "book_id" , unique = true)
    private String bookId;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "is_ready")
    private Boolean isReady;
}
