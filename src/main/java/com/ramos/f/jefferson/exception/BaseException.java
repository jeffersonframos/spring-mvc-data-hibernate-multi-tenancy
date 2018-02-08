/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author jeffe
 */
class BaseException extends Exception{
    
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public BaseException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
    
}
