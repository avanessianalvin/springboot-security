package com.vozni.springbootjwt.exception;

public class UsernameAlreadyTakenException extends RuntimeException{
    public UsernameAlreadyTakenException(){
        super("Username is already taken");
    }
}
