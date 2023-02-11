package link.hiroshisprojects.kaput.jobApplication;

import org.springframework.http.HttpStatus;

public class JobApplicationValidationException extends JobApplicationException {

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
