package link.hiroshisprojects.kaput.exceptions;

import java.time.LocalDateTime;

/**
 *	Defines the response object when an exception is thrown.
 */
public class CustomExceptionResponseBody {

	private LocalDateTime dateTime;
	private String message;
	public CustomExceptionResponseBody(LocalDateTime dateTime, String message) {
		this.dateTime = dateTime;
		this.message = message;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public String getMessage() {
		return message;
	}
	
}
