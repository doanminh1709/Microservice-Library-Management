package com.learnmicroserive.borrowingservice.command.api.controller;

import com.learnmicroserive.borrowingservice.command.api.command.CreateBorrowCommand;
import com.learnmicroserive.borrowingservice.command.api.command.UpdateBookReturnedCommand;
import com.learnmicroserive.borrowingservice.command.api.model.BorrowingRequestModel;
import com.learnmicroserive.borrowingservice.command.api.service.BorrowService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public String createBookBorrowing(@RequestBody BorrowingRequestModel model){
        try{
            CreateBorrowCommand command = new CreateBorrowCommand(
                    model.getBookId() , model.getEmployeeId() , new Date() , UUID.randomUUID().toString());
            commandGateway.sendAndWait(command);
            return "Book borrowing added success!";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PutMapping
    public String updateBookBorrowing(@RequestBody BorrowingRequestModel model){
        String id = borrowService.findIdBorrowing(model.getEmployeeId() , model.getBookId());
        if(id != null){
            UpdateBookReturnedCommand command = new UpdateBookReturnedCommand(id , model.getBookId() ,
                    model.getEmployeeId() , new Date());
            commandGateway.sendAndWait(command);
            return "Book returned success!";
        }
        return "Book return failed!";
    }
}
