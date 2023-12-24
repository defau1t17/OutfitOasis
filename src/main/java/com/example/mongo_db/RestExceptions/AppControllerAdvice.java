package com.example.mongo_db.RestExceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NewItemValidationException.class)
    public ResponseEntity<Object> handleNewItemValidationException(RuntimeException runtimeException, WebRequest request) {
        return handleExceptionInternal(runtimeException, runtimeException.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
