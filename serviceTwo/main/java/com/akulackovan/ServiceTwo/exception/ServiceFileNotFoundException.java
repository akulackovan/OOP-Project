package com.akulackovan.ServiceTwo.exception;

public class ServiceFileNotFoundException extends RuntimeException {
    public ServiceFileNotFoundException (String message) {
        super(message);
    }
}
