package com.semillero.crakruk.exeption;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){ super(message);}
}
