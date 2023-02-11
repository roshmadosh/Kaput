package link.hiroshisprojects.kaput.user;

import link.hiroshisprojects.kaput.exceptions.CustomException;

public abstract class UserException extends Exception implements CustomException {
	public UserException(String message) {
		super(message);
	}

}
