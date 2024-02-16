package com.learnmicroserive.borrowingservice.command.api.aggregate;

import com.learnmicroserive.borrowingservice.command.api.command.CreateBorrowCommand;
import com.learnmicroserive.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.learnmicroserive.borrowingservice.command.api.command.SendMessageCommand;
import com.learnmicroserive.borrowingservice.command.api.command.UpdateBookReturnedCommand;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowCreateEvent;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowDeleteEvent;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowSendMessageEvent;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowUpdateBookReturnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class BorrowAggregate {
    @AggregateIdentifier
    private String id;
    private String employeeId;
    private String bookId;
    private Date borrowingDate;
    private Date returnDate;
    private String message;

    public BorrowAggregate(){}

    @CommandHandler
    public BorrowAggregate (CreateBorrowCommand command){
        BorrowCreateEvent event = new BorrowCreateEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreateEvent event){
        this.bookId = event.getBookId();
        this.borrowingDate = event.getBorrowingDate();
        this.employeeId = event.getEmployeeId();
        this.id = event.getId();
    }

    @CommandHandler
    public void handle(DeleteBorrowCommand command){
        BorrowDeleteEvent event = new BorrowDeleteEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowDeleteEvent event){
        this.id = event.getId();
    }

    @CommandHandler
    public void handle(SendMessageCommand command){
        BorrowSendMessageEvent event = new BorrowSendMessageEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowSendMessageEvent event){
        this.id = event.getId();
        this.message = event.getMessage();
        this.employeeId = event.getMessage();
    }

    @CommandHandler
    public void handle(UpdateBookReturnedCommand command){
        BorrowUpdateBookReturnEvent event = new BorrowUpdateBookReturnEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowUpdateBookReturnEvent event){
        this.returnDate = event.getReturnDate();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
    }

}
