package com.ramos.f.jefferson.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super("Invalid access token", HttpStatus.UNAUTHORIZED);
    }
}
