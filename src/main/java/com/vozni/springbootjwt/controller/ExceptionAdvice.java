package com.vozni.springbootjwt.controller;

import com.vozni.springbootjwt.exception.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<?> usernameAlreadyTakenExceptionHandler(UsernameAlreadyTakenException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
    }
}
