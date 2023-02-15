package link.hiroshisprojects.kaput.jobapplication;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	private JobApplicationDao applicationDao;
	
	public JobApplicationServiceImpl(JobApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
	

	@Override
	public Iterable<JobApplication> getAll() {
		return applicationDao.findAll();
	}

	@Override
	public Optional<JobApplication> findJobApplicationById(long id) {
		return applicationDao.findById(id);
	}

	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #id")
	public void deleteJobApplicationById(long id) {
		applicationDao.deleteById(id);		
	}

	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #userId")
	public List<JobApplication> findJobApplicationsByUserId(long userId) {
		return applicationDao.findByUserId(userId);
	}


	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #id")
	public JobApplication updateJobApplicationById(long id, JobApplicationDTO dto) throws JobApplicationException {
		JobApplication app = applicationDao.findById(id).orElseThrow(() -> new JobApplicationNotFoundException("Job application with ID " + id + "not found."));
		app.update(dto);
		return applicationDao.save(app);
	}

}
