package link.hiroshisprojects.kaput.exceptions;

import org.springframework.http.HttpStatus;

public interface CustomException {
	
	public HttpStatus getStatusCode();
}
