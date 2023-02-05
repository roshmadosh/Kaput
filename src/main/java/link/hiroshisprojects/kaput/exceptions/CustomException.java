package link.hiroshisprojects.kaput.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends Exception {
	
	protected CustomException(String message) {
		super(message);
	}

	public abstract HttpStatus getStatusCode();
}
