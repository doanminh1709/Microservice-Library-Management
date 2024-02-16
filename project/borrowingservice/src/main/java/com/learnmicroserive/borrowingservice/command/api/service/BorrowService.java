package com.learnmicroserive.borrowingservice.command.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnmicroserive.borrowingservice.command.api.data.Borrowing;
import com.learnmicroserive.borrowingservice.command.api.data.BorrowingRepository;
import com.learnmicroserive.borrowingservice.command.api.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;



@Service
@EnableBinding(Source.class)
public class BorrowService implements IBorrowService {

    @Autowired
    private BorrowingRepository repository;

    @Autowired
    private MessageChannel output;

    @Override
    public void sendMessage(Message message) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            output.send(MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findIdBorrowing(String employeeId, String bookId) {
        return repository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId , bookId).getId();
    }
}
