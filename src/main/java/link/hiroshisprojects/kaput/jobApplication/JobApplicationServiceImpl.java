package link.hiroshisprojects.kaput.jobApplication;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	private JobApplicationDao applicationDao;

	public JobApplicationServiceImpl(JobApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	@Override
	public JobApplication save(JobApplication application) throws ConstraintViolationException {
		JobApplication savedAppliciation = applicationDao.save(application);
		return savedAppliciation;
	}

	@Override
	public Iterable<JobApplication> getAll() {
		Iterable<JobApplication> applications = applicationDao.findAll();
		return applications;
	}

	@Override
	public Optional<JobApplication> findJobApplicationById(long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteJobApplicationById(long id) {
		// TODO Auto-generated method stub
		
	}

}
