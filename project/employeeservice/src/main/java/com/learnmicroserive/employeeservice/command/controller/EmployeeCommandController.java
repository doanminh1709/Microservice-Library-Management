package com.learnmicroserive.employeeservice.command.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnmicroserive.employeeservice.command.command.CreateEmployeeCommand;
import com.learnmicroserive.employeeservice.command.command.DeleteEmployeeCommand;
import com.learnmicroserive.employeeservice.command.command.UpdateEmployeeCommand;
import com.learnmicroserive.employeeservice.command.model.EmployeeRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
//@EnableBinding(Source.class)
public class EmployeeCommandController {

   @Autowired
   private CommandGateway commandGateway;
//
//   @Autowired
//   private MessageChannel output;

    @PostMapping
   public String createEmployee(@RequestBody EmployeeRequestModel requestModel){
       CreateEmployeeCommand command = new CreateEmployeeCommand(
               UUID.randomUUID().toString() , requestModel.getFirstName() ,
               requestModel.getLastName() , requestModel.getKin() , false);
        commandGateway.sendAndWait(command);
        return "Create employee success!";
   }

   @PutMapping("/{employeeId}")
   public String updateEmployee(@PathVariable("employeeId") String employeeId,
                                @RequestBody EmployeeRequestModel requestModel){
       UpdateEmployeeCommand command = new UpdateEmployeeCommand(
               employeeId,
               requestModel.getFirstName() ,
               requestModel.getLastName() ,
               requestModel.getKin() ,
               requestModel.getIsDisciplined()
       );
       commandGateway.sendAndWait(command);
       return "Update employee success!";
   }

   @DeleteMapping("/{employeeId}")
   public String deleteEmployee(@PathVariable("employeeId") String employeeId){
       DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
       commandGateway.sendAndWait(command);
       return "Delete employee success!";
   }

//   @PostMapping("/sendMessage")
//    public void sendMessage(@RequestBody String message){
//       try{
//           ObjectMapper mapper = new ObjectMapper();
//           String json = mapper.writeValueAsString(message);
//           output.send(MessageBuilder.withPayload(json).build());
//       }catch (JsonProcessingException exception){
//           exception.printStackTrace();
//       }
//   }
}
