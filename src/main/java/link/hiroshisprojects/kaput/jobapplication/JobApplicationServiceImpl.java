package link.hiroshisprojects.kaput.jobapplication;

import java.util.List;
import java.util.Optional;

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
	public void deleteJobApplicationById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JobApplication> findJobApplicationsByUserId(long userId) {
		return applicationDao.findByUserId(userId);
	}

}
