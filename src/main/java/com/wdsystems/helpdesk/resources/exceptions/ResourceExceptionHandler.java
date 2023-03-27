package com.wdsystems.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wdsystems.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.wdsystems.helpdesk.services.exceptions.InvalidCredentialException;
import com.wdsystems.helpdesk.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis()
											  , HttpStatus.NOT_FOUND.value()
											  , "Object Not Found"
											  , ex.getMessage()
											  , request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis()
											  , HttpStatus.BAD_REQUEST.value()
											  , "Data Integrity Violation"
											  , ex.getMessage()
											  , request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErros(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		ValidationError errors = new ValidationError(System.currentTimeMillis()
												   , HttpStatus.BAD_REQUEST.value()
												   , "Method Argument Not Valid"
												   , "Validation error"
												   , request.getRequestURI());

		for (FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> validationErros(ConstraintViolationException ex, HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis()
											  , HttpStatus.BAD_REQUEST.value()
											  , "Constraint Violation"
											  , "Invalid CPF"
											  , request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<StandardError> invalidCredentialException(InvalidCredentialException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis()
											  , HttpStatus.UNAUTHORIZED.value()
											  , "Unauthorized"
											  , "Invalid email or password"
											  , request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
}
