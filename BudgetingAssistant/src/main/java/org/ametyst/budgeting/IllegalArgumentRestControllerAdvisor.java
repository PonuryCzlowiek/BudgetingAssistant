package org.ametyst.budgeting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentRestControllerAdvisor {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseMessage> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseMessage(e.getMessage()));
    }
}
