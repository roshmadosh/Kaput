package link.hiroshisprojects.kaput.user;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {

	public UserNotFoundException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}

}

