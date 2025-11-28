package com.saar.ratingservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String,Object>>notFoundHandler(ResourceNotFoundException ex)
	{
		Map mp=new HashMap<>();
		mp.put("message", ex.getMessage());
		mp.put("success", false);
		mp.put("status", HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mp);
				}

}
