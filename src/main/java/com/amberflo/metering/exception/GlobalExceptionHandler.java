package com.amberflo.metering.exception;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.amberflo.metering.entity.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException ce) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ce.hashCode(), new Date(), ce.getMessage())); 
    }
    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
    	ErrorResponse message = new ErrorResponse(
          ex.hashCode(),
          new Date(),
          ex.getMessage());
      return new ResponseEntity<ErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> globalIOExceptionHandler(IOException ex, WebRequest request) {
    	ErrorResponse message = new ErrorResponse(
          ex.hashCode(),
          new Date(),
          ex.getMessage());
      return new ResponseEntity<ErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
