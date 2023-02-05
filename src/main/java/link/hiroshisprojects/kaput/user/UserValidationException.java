package link.hiroshisprojects.kaput.user;

import org.springframework.http.HttpStatus;

public class UserValidationException extends UserException {

	public UserValidationException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}


}

