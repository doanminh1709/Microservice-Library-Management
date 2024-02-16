package com.learnmicroserive.bookservice.command.event;

import com.learnmicroserive.bookservice.command.data.Book;
import com.learnmicroserive.bookservice.command.data.BookRepository;
import com.learnmicroserive.commonserive.events.BookRollBackStatusEvent;
import com.learnmicroserive.commonserive.events.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {

    @Autowired
    private BookRepository bookRepository;
    //(Sau khi trải qua quá trình proess bên aggregate thì nó sẽ nhảy vào hàm tương ứng , đúng với cái event tương ứng
    // nó sẽ tự động mapping vào đúng hàm handler mà mình gửi 1 cái event tương ứng)

    //when it jumps to event sourcing handler and update book aggregate, and it continues jump to event handler ,
    //automatic detect this function mapping book event
    @EventHandler
    public void on(BookCreateEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event , book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdateEvent event){
        Book book = bookRepository.findById(event.getBookId()).get();
        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookDeleteEvent event){
        try{
            Optional<Book> book = bookRepository.findById(event.getBookId());
            if(book.isPresent()){
                bookRepository.deleteById(event.getBookId());
            }else{
                throw new Exception("Employee is not exists!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event){
        Book book = bookRepository.findById(event.getBookId()).get();
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookRollBackStatusEvent event){
        Book book = bookRepository.getById(event.getBookId());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
}
