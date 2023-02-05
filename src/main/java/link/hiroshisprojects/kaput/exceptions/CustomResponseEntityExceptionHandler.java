package link.hiroshisprojects.kaput.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import link.hiroshisprojects.kaput.user.UserException;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CustomExceptionResponseBody> defaultHandler(Exception e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(LocalDateTime.now(), e.getMessage());

		return ResponseEntity.internalServerError().body(resp);
	}

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<CustomExceptionResponseBody> userHandler(UserException e, WebRequest request) {
		CustomExceptionResponseBody resp = new CustomExceptionResponseBody(LocalDateTime.now(), e.getMessage());

		return new ResponseEntity<CustomExceptionResponseBody>(resp, e.getStatusCode()); 
	}

}
