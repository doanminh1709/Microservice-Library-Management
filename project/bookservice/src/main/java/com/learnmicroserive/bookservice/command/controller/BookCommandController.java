package com.learnmicroserive.bookservice.command.controller;

import com.learnmicroserive.bookservice.command.command.CreateBookCommand;
import com.learnmicroserive.bookservice.command.command.DeleteBookCommand;
import com.learnmicroserive.bookservice.command.command.UpdateBookCommand;
import com.learnmicroserive.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    @Autowired
    private CommandGateway commandGateway;//represent for single port(gateway) react with command model of system.

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model){
        CreateBookCommand command = new CreateBookCommand(
                UUID.randomUUID().toString(),
                model.getName() ,
                model.getAuthor() ,
                true
        );
        //sent an event
        commandGateway.sendAndWait(command);
        return "Added Book Success!";
    }

    @PutMapping
    public String updateBook(@RequestBody BookRequestModel model){
        UpdateBookCommand command = new UpdateBookCommand(
                model.getBookId(),
                model.getName() ,
                model.getAuthor() ,
                model.getIsReady()
        );
        commandGateway.sendAndWait(command);
        return "Update Book Success!";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") String bookId){
        DeleteBookCommand command = new DeleteBookCommand(bookId);
        commandGateway.sendAndWait(command);
        return "Delete Book Success!";
    }

}
