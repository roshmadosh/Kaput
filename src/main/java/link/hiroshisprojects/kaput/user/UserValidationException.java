package link.hiroshisprojects.kaput.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserValidationException extends Exception {

	public UserValidationException(String message) {
		super(message);
	}


}

