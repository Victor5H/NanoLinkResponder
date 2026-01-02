package com.harshit.NanoLinkResponder.exception;

public class NoSuchUrlException extends RuntimeException {
    public NoSuchUrlException(String reason){
        super(reason);
    }
}
