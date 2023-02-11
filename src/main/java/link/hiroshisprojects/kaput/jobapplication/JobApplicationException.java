package link.hiroshisprojects.kaput.jobapplication;

import link.hiroshisprojects.kaput.exceptions.CustomException;

public abstract class JobApplicationException extends Exception implements CustomException {

	public JobApplicationException(String message) {
		super(message);
	}
}
