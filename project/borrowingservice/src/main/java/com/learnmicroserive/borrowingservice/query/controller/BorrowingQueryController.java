package com.learnmicroserive.borrowingservice.query.controller;

import com.learnmicroserive.borrowingservice.query.model.BorrowingResponseModel;
import com.learnmicroserive.borrowingservice.query.queries.GetAllBorrowing;
import com.learnmicroserive.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingQueryController {

    //get borrowing by employee
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public List<BorrowingResponseModel> getBorrowingByEmployeeId(@PathVariable String employeeId){
        GetListBorrowingByEmployeeQuery query = new GetListBorrowingByEmployeeQuery(employeeId);
        return queryGateway.query(query , ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class)).join();
    }

    //get all borrowing
    @GetMapping
    public List<BorrowingResponseModel> getAllBorrowing(){

        return queryGateway.query(new GetAllBorrowing(),
                ResponseTypes.multipleInstancesOf(BorrowingResponseModel.class)).join();
    }

}
