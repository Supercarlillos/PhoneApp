package com.phone.app.controller;


import com.phone.app.domain.Error;
import com.phone.app.domain.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ControllerAdviceError {

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<Error> badRequest(final ConstraintViolationException ex) {
		return ex.getConstraintViolations().stream()
				.map(constraintViolation -> Error.builder().field(constraintViolation.getPropertyPath().toString())
						.description(constraintViolation.getMessage()).build()).collect(Collectors.toList());

	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<Error> badRequest(final ValidationException ex) {
		return ex.getErrors();

	}



	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error handleError(final Exception exception) {
		return Error.builder().description(exception.getMessage()).build();
	}

}