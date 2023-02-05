package link.hiroshisprojects.kaput.user;

import link.hiroshisprojects.kaput.exceptions.CustomException;

public abstract class UserException extends CustomException {

	protected UserException(String message) {
		super(message);
	}

}
