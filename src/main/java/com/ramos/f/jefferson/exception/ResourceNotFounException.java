package com.ramos.f.jefferson.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFounException extends BaseException{
    
    public ResourceNotFounException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
    
}
