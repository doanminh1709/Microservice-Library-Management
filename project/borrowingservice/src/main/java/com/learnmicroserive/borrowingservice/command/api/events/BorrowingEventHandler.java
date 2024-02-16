package com.learnmicroserive.borrowingservice.command.api.events;

import com.learnmicroserive.borrowingservice.command.api.data.Borrowing;
import com.learnmicroserive.borrowingservice.command.api.data.BorrowingRepository;
import com.learnmicroserive.borrowingservice.command.api.model.Message;
import com.learnmicroserive.borrowingservice.command.api.service.BorrowService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BorrowingEventHandler {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BorrowService borrowService;
    @EventHandler
    public void on(BorrowCreateEvent event){
        Borrowing model = new Borrowing();
        BeanUtils.copyProperties(event , model);
        borrowingRepository.save(model);
    }

    @EventHandler
    public void on(BorrowDeleteEvent event){
        if(borrowingRepository.findById(event.getId()).isPresent()){
            borrowingRepository.deleteById(event.getId());
        }
    }

    @EventHandler
    public void on(BorrowSendMessageEvent event){
        Message message = new Message(event.getEmployeeId() , event.getMessage());
        borrowService.sendMessage(message);
    }

    @EventHandler
    public void on(BorrowUpdateBookReturnEvent event){
        Borrowing borrowing = borrowingRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(event.getEmployeeId() , event.getBookId());
        System.out.println(borrowing);
        if(borrowing != null){
            borrowing.setReturnDate(new Date());
            borrowingRepository.save(borrowing);
        }
    }
}
