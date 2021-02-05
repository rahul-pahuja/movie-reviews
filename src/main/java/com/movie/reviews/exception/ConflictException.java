package com.movie.reviews.exception;

public class ConflictException extends Exception {

	private static final long serialVersionUID = -969903082352946758L;

	private String message;

	public ConflictException() {
		super();
	}

	public ConflictException(String message) {
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
