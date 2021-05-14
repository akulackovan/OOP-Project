package com.akulackovan.Service.exeption;

public class ServiceFileNotFoundException extends RuntimeException {
    public ServiceFileNotFoundException (String message) {
        super(message);
    }
}
