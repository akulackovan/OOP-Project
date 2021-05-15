package com.akulackovan.ServiceThree.exception;

public class ServiceFileNotFoundException extends RuntimeException {
    public ServiceFileNotFoundException (String message) {
        super(message);
    }
}
