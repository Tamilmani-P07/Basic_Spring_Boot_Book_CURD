package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(value = CustomException.class)
	 public ResponseEntity<String> handleValidationException(CustomException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
}
