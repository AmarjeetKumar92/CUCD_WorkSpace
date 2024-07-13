package com.example.rqchallenge.exception;

import com.example.rqchallenge.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class EmployeeGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        ApiResponse response = new ApiResponse("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoEmployeesFoundException.class)
    public ResponseEntity<ApiResponse> handleNoEmployeesFoundException(NoEmployeesFoundException ex, WebRequest request) {
        ApiResponse response = new ApiResponse("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ApiResponse response = new ApiResponse("error", "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
