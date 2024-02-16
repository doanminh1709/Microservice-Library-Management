package com.learnmicroserive.borrowingservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListBorrowingByEmployeeQuery {
    private String employeeId;
}
