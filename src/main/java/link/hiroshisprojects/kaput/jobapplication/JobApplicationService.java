package link.hiroshisprojects.kaput.jobapplication;

import java.util.List;
import java.util.Optional;

public interface JobApplicationService {

	public Iterable<JobApplication> getAll();

	public Optional<JobApplication> findJobApplicationById(long id);

	public List<JobApplication> findJobApplicationsByUserId(long userId);

	public void deleteJobApplicationById(long id);

	public JobApplication updateJobApplicationById(long id, JobApplicationDTO dto) throws JobApplicationException;

}
