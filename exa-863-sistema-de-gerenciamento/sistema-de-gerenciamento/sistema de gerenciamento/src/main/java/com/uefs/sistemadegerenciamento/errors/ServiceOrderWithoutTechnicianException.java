package com.uefs.sistemadegerenciamento.errors;

public class ServiceOrderWithoutTechnicianException extends Exception{
    public ServiceOrderWithoutTechnicianException(String message) {
        super(message);
    }
}
