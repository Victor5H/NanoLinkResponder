package com.harshit.NanoLinkResponder;

import com.harshit.NanoLinkResponder.exception.NoSuchUrlException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchUrlException.class)
    public ResponseEntity<?> noSuchUrl(){
        return ResponseEntity.badRequest().body("No such url");
    }
}
