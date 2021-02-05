package com.movie.reviews.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 5777016204965477601L;

	private String message;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
