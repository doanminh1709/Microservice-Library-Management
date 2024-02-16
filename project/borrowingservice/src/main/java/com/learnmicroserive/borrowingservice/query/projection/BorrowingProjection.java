package com.learnmicroserive.borrowingservice.query.projection;

import com.learnmicroserive.borrowingservice.command.api.data.Borrowing;
import com.learnmicroserive.borrowingservice.command.api.data.BorrowingRepository;
import com.learnmicroserive.borrowingservice.query.model.BorrowingResponseModel;
import com.learnmicroserive.borrowingservice.query.queries.GetAllBorrowing;
import com.learnmicroserive.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.learnmicroserive.commonserive.model.BookResponseCommonModel;
import com.learnmicroserive.commonserive.model.EmployeeResponseCommonModel;
import com.learnmicroserive.commonserive.query.GetDetailBookQuery;
import com.learnmicroserive.commonserive.query.GetDetailEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingProjection {

    //todo : get all borrowing by employee id
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> getAllBorrowingByEmployeeId(GetListBorrowingByEmployeeQuery query){
        List<Borrowing> borrowings = borrowingRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId());
        List<BorrowingResponseModel> borrowingResponseModels = new ArrayList<BorrowingResponseModel>();
        borrowings.forEach(item ->{
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(item , model);
            model.setNameEmployee(queryGateway.query(new GetDetailEmployeeQuery(model.getEmployeeId())
                    , ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join().getFirstName());
            model.setNameBook(queryGateway.query(new GetDetailBookQuery(model.getBookId()) ,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join().getName());

            borrowingResponseModels.add(model);
        });
        return borrowingResponseModels;
    }

    //Todo : Get all borrowing
    @QueryHandler
    public List<BorrowingResponseModel> getAllBorrowing(GetAllBorrowing query){
        List<Borrowing> borrowings = borrowingRepository.findAll();
        List<BorrowingResponseModel> borrowingResponseModels = new ArrayList<>();
        borrowings.forEach(item ->{
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(item , model);
            model.setNameEmployee(queryGateway.query(new GetDetailEmployeeQuery(model.getEmployeeId())
                    , ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join().getFirstName());
            model.setNameBook(queryGateway.query(new GetDetailBookQuery(model.getBookId()) ,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join().getName());
            borrowingResponseModels.add(model);
        });
        return borrowingResponseModels;
    }
}
