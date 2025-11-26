package com.saar.userservice.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException() {
		super("Resource not found on servier !!!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
