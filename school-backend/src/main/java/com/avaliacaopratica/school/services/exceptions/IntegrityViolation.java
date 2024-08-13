package com.avaliacaopratica.school.services.exceptions;

public class IntegrityViolation extends RuntimeException {

    public IntegrityViolation(String message) {
        super(message);
    }

}