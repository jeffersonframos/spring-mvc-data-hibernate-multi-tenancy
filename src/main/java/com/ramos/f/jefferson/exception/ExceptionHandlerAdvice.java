/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author jeffe
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    
    @ExceptionHandler(SaveException.class)
    public ResponseEntity handleException(SaveException exception){
        return throwException(exception);
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleException(ForbiddenException exception){
        return throwException(exception);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleException(UnauthorizedException exception){
        return throwException(exception);
    }
    
    @ExceptionHandler(ResourceNotFounException.class)
    public ResponseEntity handleException(ResourceNotFounException exception){
        return throwException(exception);
    }
    
    private ResponseEntity throwException(BaseException baseException){
        return ResponseEntity.status(baseException.getHttpStatus()).body(baseException.getMessage());
    }
    
}
