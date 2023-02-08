package com.crud.exceptions;

public class NotFoundPostException extends RuntimeException{
    public NotFoundPostException(){
        super("The post is not exist");
    }
}
