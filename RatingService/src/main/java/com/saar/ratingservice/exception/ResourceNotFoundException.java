package com.saar.ratingservice.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException () {
		super("Resource not found");
	}
	
	public ResourceNotFoundException (String msg) {
		super(msg);
	}

}
