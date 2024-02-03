package com.blogapp.exceptions;

import com.blogapp.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(new ApiResponse(message, false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> res = new HashMap<>();

//        for(ObjectError error : ex.getBindingResult().getAllErrors()){
//            String fieldName = ((FieldError) error).getField();
//            String fieldMessage = error.getDefaultMessage();
//            res.put(fieldName, fieldMessage);
//        }
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String fieldMessage = error.getDefaultMessage();
            res.put(fieldName, fieldMessage);
        });
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
