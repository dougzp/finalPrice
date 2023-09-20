package com.gft.inditex.finalprice.infra.adapters.in.exceptions;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(String message) {
        super(message);
    }
}