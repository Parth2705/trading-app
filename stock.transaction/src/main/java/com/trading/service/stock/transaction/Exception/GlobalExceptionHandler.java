package com.trading.service.stock.transaction.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value= { EntityNotFoundException.class })
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException e)
    {
        log.error("User service: EntityNotFoundException occurred at " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": "+e.getMessage());
        return new ResponseEntity<String>("Exception occurred \n\t"+ e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value= { NullPointerException.class })
    public ResponseEntity<String> nullPointerException(NullPointerException e)
    {
        log.error("User service: EntityNotFoundException occurred at " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": "+e.getMessage());
        return new ResponseEntity<String>("Exception occurred \n\t"+ e.getMessage(),HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value= { Exception.class })
    public ResponseEntity<String> unexpectedException(Exception e)
    {
        log.error("User service: Exception occurred at " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": "+e.getMessage());
        return new ResponseEntity<String>("Exception occurred \n\t"+ e.getMessage(),HttpStatus.BAD_REQUEST);
    }

}
