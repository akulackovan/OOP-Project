package com.akulackovan.Service.exception;

public class ServiceFileNotFoundException extends RuntimeException {
    public ServiceFileNotFoundException (String message) {
        super(message);
    }
}
