package com.learnmicroserive.borrowingservice.command.api.saga;

import com.learnmicroserive.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.learnmicroserive.borrowingservice.command.api.command.SendMessageCommand;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowCreateEvent;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowDeleteEvent;
import com.learnmicroserive.borrowingservice.command.api.events.BorrowUpdateBookReturnEvent;
import com.learnmicroserive.commonserive.command.RollBackStatusBookCommand;
import com.learnmicroserive.commonserive.command.UpdateStatusBookCommand;
import com.learnmicroserive.commonserive.events.BookRollBackStatusEvent;
import com.learnmicroserive.commonserive.events.BookUpdateStatusEvent;
import com.learnmicroserive.commonserive.model.BookResponseCommonModel;
import com.learnmicroserive.commonserive.model.EmployeeResponseCommonModel;
import com.learnmicroserive.commonserive.query.GetDetailBookQuery;
import com.learnmicroserive.commonserive.query.GetDetailEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowingSaga {

    //transient : This field don't join to serialization process
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreateEvent event){
        System.out.println("BorrowCreateEvent in Saga for BookId : " + event.getBookId() + " : EmployeeId : "+event.getEmployeeId());
        try{
            //start associate with book id
            SagaLifecycle.associateWith("bookId" , event.getBookId());

            BookResponseCommonModel bookResponseCommonModel = queryGateway.query(new GetDetailBookQuery(event.getBookId()) ,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();

            System.out.println(bookResponseCommonModel);
            if(bookResponseCommonModel.getIsReady()){
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId() ,
                        false , event.getEmployeeId() , event.getId());
                commandGateway.sendAndWait(command);
            }else{
                throw new Exception("This book is borrowed !");
            }
        }catch (Exception e){
            rollBackBorrowRecord(event.getId());
            System.out.println(e.getMessage());
        }
    }
    private void rollBackBorrowRecord(String id){
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }
    //check employee is disciplined or not , if disciplined is true so callback data
    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event) {
        System.out.println("BookUpdateStatusEvent in Saga for BookId : "+event.getBookId());
        try {
            EmployeeResponseCommonModel employeeResponseCommonModel =
                    queryGateway.query(new GetDetailEmployeeQuery(event.getEmployeeId()), ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
                            .join();

            System.out.println(employeeResponseCommonModel);
            if(employeeResponseCommonModel.getIsDisciplined()) {
                throw new Exception("This employee is disciplined!");
            }else {
                commandGateway.sendAndWait(new SendMessageCommand(event.getBorrowId(), event.getEmployeeId(), "Borrow book success !"));
                SagaLifecycle.end();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rollBackBookStatus(event.getBookId(),event.getEmployeeId(),event.getBorrowId());
        }
    }
    private void rollBackBookStatus(String bookId , String employeeId , String borrowId){
        System.out.println("ROLL BACK STATUS BOOK");
        SagaLifecycle.associateWith("bookId" , bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId , true , employeeId , borrowId);
        commandGateway.sendAndWait(command);
    }
    @SagaEventHandler(associationProperty = "bookId")
    private void handleRollBackBookStatus(BookRollBackStatusEvent event){
       try{
           System.out.println("Handle rollback book status with book id : "+ event.getBookId());
           rollBackBorrowRecord(event.getBorrowId());
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }

    //Saga need to event base on id
    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(BorrowDeleteEvent event){
        System.out.println("Borrow delete event in saga with borrow id : " + event.getId());
        SagaLifecycle.end();
    }
    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowUpdateBookReturnEvent event){
        System.out.println("Borrowing update book return event with borrow id : " + event.getId());
        try{
            UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(),true ,
                    event.getEmployeeId() , event.getId());
            String message = String.format("Employee with id  : %s  returned book success!", event.getEmployeeId());
            //send command update status book
            commandGateway.sendAndWait(command);
            //send message to notification
            commandGateway.sendAndWait(new SendMessageCommand(event.getId(), event.getEmployeeId() , message));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

