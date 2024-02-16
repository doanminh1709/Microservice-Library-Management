package com.learnmicroserive.bookservice.query.projection;

import com.learnmicroserive.bookservice.command.data.Book;
import com.learnmicroserive.bookservice.command.data.BookRepository;
import com.learnmicroserive.bookservice.query.model.BookResponseModel;
import com.learnmicroserive.bookservice.query.queries.GetAllBookQuery;
import com.learnmicroserive.bookservice.query.queries.GetBookQuery;
import com.learnmicroserive.commonserive.model.BookResponseCommonModel;
import com.learnmicroserive.commonserive.query.GetDetailBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {//like command handler to handle projection

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery){
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.getById(getBookQuery.getBookId());
        BeanUtils.copyProperties(book , model);
        return model;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery getAllBookQuery){
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseModel> listBook = new ArrayList<>();
        listEntity.forEach(item ->{
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(item , model);
            listBook.add(model);
        });
        return listBook;
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetDetailBookQuery getDetailBookQuery){
        BookResponseCommonModel model = new BookResponseCommonModel();
        Book book = bookRepository.getById(getDetailBookQuery.getBookId());
        BeanUtils.copyProperties(book , model);
        return model;
    }
}
