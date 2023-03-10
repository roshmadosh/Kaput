package link.hiroshisprojects.kaput.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import link.hiroshisprojects.kaput.authentication.CustomAuthenticationException;
import link.hiroshisprojects.kaput.jobapplication.JobApplicationException;
import link.hiroshisprojects.kaput.user.UserException;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CustomExceptionResponseBody> defaultHandler(Exception e, WebRequest request) {
		e.printStackTrace();
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getMessage());

		return ResponseEntity.internalServerError().body(resp);
	}

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<CustomExceptionResponseBody> userHandler(UserException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getLocalizedMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, e.getStatusCode()); 
	}

	@ExceptionHandler(CustomAuthenticationException.class)
	public final ResponseEntity<CustomExceptionResponseBody> authHandler(CustomAuthenticationException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, e.getStatusCode()); 
	}

	@ExceptionHandler(JobApplicationException.class)
	public final ResponseEntity<CustomExceptionResponseBody> jobApplicationHandler(JobApplicationException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, e.getStatusCode()); 
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public final ResponseEntity<CustomExceptionResponseBody> emptyResultHandler(EmptyResultDataAccessException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<CustomExceptionResponseBody> accessDeniedHandler(AccessDeniedException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<CustomExceptionResponseBody> badCredentialsHandler(AuthenticationException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(e.getClass().getName()+ ": " + 
				e.getMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, HttpStatus.BAD_REQUEST);
	}

	/* For handling deserialization exceptions. */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(ex.getCause().getMessage());

		return new ResponseEntity<Object>(resp, status);
	}

	/* Returns a list of validation error messages. */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errMessages = ex.getFieldErrors().stream().map(error -> error.getDefaultMessage())
			.collect(Collectors.toList());	

		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(errMessages.toString());
		
		return new ResponseEntity<Object>(resp, HttpStatus.BAD_REQUEST); 
	}

}
