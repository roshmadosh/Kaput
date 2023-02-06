package link.hiroshisprojects.kaput.exceptions;

import java.time.LocalDateTime;

/**
 *	Defines the response object when an exception is thrown.
 */
public class CustomExceptionResponseBody {

	private LocalDateTime dateTime;
	private String message;
	public CustomExceptionResponseBody(String message) {
		this.dateTime = LocalDateTime.now();
		this.message = message;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public String getMessage() {
		return message;
	}
	
}
