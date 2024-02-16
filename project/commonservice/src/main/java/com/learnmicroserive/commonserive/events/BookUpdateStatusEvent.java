package com.learnmicroserive.commonserive.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateStatusEvent {
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;
}
