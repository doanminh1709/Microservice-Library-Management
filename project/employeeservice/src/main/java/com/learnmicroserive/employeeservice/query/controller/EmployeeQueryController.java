package com.learnmicroserive.employeeservice.query.controller;

import com.learnmicroserive.employeeservice.EmployeeserviceApplication;
import com.learnmicroserive.employeeservice.query.model.EmployeeResponseModel;
import com.learnmicroserive.employeeservice.query.queries.GetAllEmployeeQuery;
import com.learnmicroserive.employeeservice.query.queries.GetEmployeeQuery;
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
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    private Logger logger = LoggerFactory.getLogger(EmployeeserviceApplication.class);

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeeQuery getEmployeesQuery = new GetEmployeeQuery();
        getEmployeesQuery.setEmployeeId(employeeId);

        EmployeeResponseModel employeeReponseModel =
                queryGateway.query(getEmployeesQuery,
                                ResponseTypes.instanceOf(EmployeeResponseModel.class))
                        .join();

        return employeeReponseModel;
    }

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees(){

        List<EmployeeResponseModel> list = queryGateway.query(new GetAllEmployeeQuery() ,
                ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
        logger.info(list.toString());
        return list;
    }

}
