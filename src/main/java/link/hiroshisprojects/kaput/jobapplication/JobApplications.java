package link.hiroshisprojects.kaput.jobapplication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

public class JobApplications extends RepresentationModel<JobApplications> {

	private	Set<JobApplication> applications = new HashSet<>();

	public Set<JobApplication> getApplications() {
		return applications;
	}

	public void setApplications(Set<JobApplication> applications) {
		this.applications = applications;
	}

}
