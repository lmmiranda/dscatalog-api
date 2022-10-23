package com.devsuperior.dscatalogapi.exceptionhandler.excpetions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
