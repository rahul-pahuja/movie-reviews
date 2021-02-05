package com.movie.reviews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.movie.reviews.utils.ProjectConstants;

@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ClientError handleBadRequestException(final BadRequestException exception) {
		ClientError clientError = new ClientError(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
		return clientError;
	}

	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ClientError handleBadRequestException(final ConflictException exception) {
		ClientError clientError = new ClientError(exception.getMessage(), HttpStatus.CONFLICT.value());
		return clientError;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ClientError handleBadRequestException(final ResourceNotFoundException exception) {
		ClientError clientError = new ClientError(exception.getMessage(), HttpStatus.NOT_FOUND.value());
		return clientError;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ClientError handleBadRequestException(final Exception exception) {
		System.out.println(exception.getStackTrace());
		ClientError clientError = new ClientError(ProjectConstants.INTERNAL_SERVER_EXCEPTION_MESSAGE,
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return clientError;
	}

}
