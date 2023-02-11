package link.hiroshisprojects.kaput.jobapplication;

import org.springframework.http.HttpStatus;

public class JobApplicationValidationException extends JobApplicationException {

	public JobApplicationValidationException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
