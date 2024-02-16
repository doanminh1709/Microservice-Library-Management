package com.learnmicroserive.employeeservice.command.event;

import com.learnmicroserive.employeeservice.command.data.Employee;
import com.learnmicroserive.employeeservice.command.data.EmployeeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeEventsHandler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void handler(EmployeeCreateEvent event){
        Employee employee = new Employee();
        BeanUtils.copyProperties(event , employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void handler(EmployeeUpdateEvent event){
        Employee employee = employeeRepository.getById(event.getEmployeeId());
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDisciplined(event.getIsDisciplined());
        employeeRepository.save(employee);
    }

    @EventHandler
    public void handler(EmployeeDeleteEvent event){
        try{
            Optional<Employee> employee = employeeRepository.findById(event.getEmployeeId());
            if(employee.isPresent()){
                employeeRepository.deleteById(event.getEmployeeId());
            }else{
                throw new Exception("Employee is not exists!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
