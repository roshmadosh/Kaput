package link.hiroshisprojects.kaput.jobapplication;

import org.springframework.http.HttpStatus;

public class JobApplicationNotFoundException extends JobApplicationException {

	public JobApplicationNotFoundException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}

}
