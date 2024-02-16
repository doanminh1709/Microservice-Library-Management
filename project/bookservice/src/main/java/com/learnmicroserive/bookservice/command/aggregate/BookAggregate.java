package com.learnmicroserive.bookservice.command.aggregate;

import com.learnmicroserive.bookservice.command.command.CreateBookCommand;
import com.learnmicroserive.bookservice.command.command.DeleteBookCommand;
import com.learnmicroserive.bookservice.command.command.UpdateBookCommand;
import com.learnmicroserive.bookservice.command.event.BookCreateEvent;
import com.learnmicroserive.bookservice.command.event.BookDeleteEvent;
import com.learnmicroserive.bookservice.command.event.BookUpdateEvent;
import com.learnmicroserive.commonserive.command.RollBackStatusBookCommand;
import com.learnmicroserive.commonserive.command.UpdateStatusBookCommand;
import com.learnmicroserive.commonserive.events.BookRollBackStatusEvent;
import com.learnmicroserive.commonserive.events.BookUpdateStatusEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {

    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate(){}

    //After controller sent commend , next It will run here. When have command , We can creat event like this command
    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand){
        BookCreateEvent bookCreateEvent = new BookCreateEvent();
        BeanUtils.copyProperties(createBookCommand , bookCreateEvent);
        //bookCreateEvent.setAuthor(createBookCommand.getAuthor);
        //Sent event
        AggregateLifecycle.apply(bookCreateEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand){
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(updateBookCommand , bookUpdateEvent);
        AggregateLifecycle.apply(bookUpdateEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand){
        BookDeleteEvent bookDeleteEvent = new BookDeleteEvent();
        BeanUtils.copyProperties(deleteBookCommand , bookDeleteEvent);
        AggregateLifecycle.apply(bookDeleteEvent);
    }
    @CommandHandler
    public void handle(UpdateStatusBookCommand command){
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }
    //After sent event , it will jump this function which get data of event and update for book aggregate , it changes
    //all property.(Get data from event to update data for aggregate)

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event){
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }
    @EventSourcingHandler
    public void on(BookCreateEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event){
        this.bookId = event.getBookId();
    }

    @CommandHandler
    public void handle(RollBackStatusBookCommand command){
        BookRollBackStatusEvent event = new BookRollBackStatusEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookRollBackStatusEvent event){
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }
}
