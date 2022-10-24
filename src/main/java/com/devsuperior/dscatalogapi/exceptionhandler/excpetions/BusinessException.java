package com.devsuperior.dscatalogapi.exceptionhandler.excpetions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
