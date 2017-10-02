package com.phone.app.domain.exceptions;

import com.phone.app.domain.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationException extends Exception {

	private final List<Error> errors;
}
