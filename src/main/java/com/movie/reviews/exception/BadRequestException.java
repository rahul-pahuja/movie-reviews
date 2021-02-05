package com.movie.reviews.exception;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = -3185180288081386673L;

	private String message;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
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
