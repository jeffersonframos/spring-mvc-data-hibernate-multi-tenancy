package com.ramos.f.jefferson.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
