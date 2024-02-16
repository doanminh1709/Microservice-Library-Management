package com.learnmicroserive.employeeservice.query.projection;

import com.learnmicroserive.commonserive.model.EmployeeResponseCommonModel;
import com.learnmicroserive.commonserive.query.GetDetailEmployeeQuery;
import com.learnmicroserive.employeeservice.command.data.Employee;
import com.learnmicroserive.employeeservice.command.data.EmployeeRepository;
import com.learnmicroserive.employeeservice.query.model.EmployeeResponseModel;
import com.learnmicroserive.employeeservice.query.queries.GetAllEmployeeQuery;
import com.learnmicroserive.employeeservice.query.queries.GetEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeQuery getEmployeeQuery){
        EmployeeResponseModel responseModel = new EmployeeResponseModel();
        Employee employee = employeeRepository.getById(getEmployeeQuery.getEmployeeId());
        BeanUtils.copyProperties(employee , responseModel);
        return responseModel;
    }

    @QueryHandler
    public EmployeeResponseCommonModel handle(GetDetailEmployeeQuery query){
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        Employee employee = employeeRepository.getById(query.getEmployeeId());
        BeanUtils.copyProperties(employee , model);
        return model;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery getAllEmployeeQuery){

        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponseModel> modelList = new ArrayList<>();
        employeeList.forEach(item ->{
            EmployeeResponseModel employeeModel = new EmployeeResponseModel();
            BeanUtils.copyProperties(item , employeeModel);
            modelList.add(employeeModel);
                });
        return modelList;
    }
}
