package link.hiroshisprojects.kaput.jobApplication;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

public interface JobApplicationService {

	public JobApplication save(JobApplication application) throws ConstraintViolationException;	

	public Iterable<JobApplication> getAll();

	public Optional<JobApplication> findJobApplicationById(long id);

	public void deleteJobApplicationById(long id);

}
