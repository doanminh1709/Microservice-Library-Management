package com.learnmicroserive.bookservice.query.controller;

import com.learnmicroserive.bookservice.BookserviceApplication;
import com.learnmicroserive.bookservice.query.model.BookResponseModel;
import com.learnmicroserive.bookservice.query.queries.GetAllBookQuery;
import com.learnmicroserive.bookservice.query.queries.GetBookQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;//Using query in CQRS model

    private Logger logger = LoggerFactory.getLogger(BookserviceApplication.class);

    @GetMapping("/{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId){
        GetBookQuery getBookQuery = new GetBookQuery(bookId);
        BookResponseModel bookResponseModel = queryGateway
                .query(getBookQuery , ResponseTypes.instanceOf(BookResponseModel.class)).join();
        //TODO : ResponseTypes : Kind of type response after call join method to join result
        return bookResponseModel;

    }

    @GetMapping
    public List<BookResponseModel> getAllBooks(){
        List<BookResponseModel> list =
                queryGateway.query(new GetAllBookQuery() , ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
        logger.info(list.toString());
        return list;
    }

}
