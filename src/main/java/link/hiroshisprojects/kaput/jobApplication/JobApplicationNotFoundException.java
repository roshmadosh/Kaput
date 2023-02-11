package link.hiroshisprojects.kaput.jobApplication;

import org.springframework.http.HttpStatus;

public class JobApplicationNotFoundException extends JobApplicationException {

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}

}
