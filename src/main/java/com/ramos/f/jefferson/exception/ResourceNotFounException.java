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
public class ResourceNotFounException extends BaseException{
    
    public ResourceNotFounException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
    
}
