package com.example.GreenNest.exception;

public class InsufficientAmountException extends RuntimeException{

    public InsufficientAmountException(String msg){
        super(msg);
    }
}
