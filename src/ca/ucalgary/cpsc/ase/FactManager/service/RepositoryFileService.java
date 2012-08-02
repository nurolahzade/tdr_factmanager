package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.entity.RepositoryFile;
import ca.ucalgary.cpsc.ase.FactManager.entity.VisitState;

public class RepositoryFileService extends AbstractService<RepositoryFile> {

	public RepositoryFileService() {
		super(RepositoryFile.class);
	}
	
	public RepositoryFile create(String path) {
		beginTransaction();
		RepositoryFile file = new RepositoryFile();
		file.setPath(path);
		file.setState(VisitState.NOT_VISITED);
		create(file);
		commitTransaction();
		
		return file;
	}
	
	public void visit(RepositoryFile file) {
		beginTransaction();
		file.setState(VisitState.VISITED);
		update(file);
		commitTransaction();
	}
	
	public void skip(RepositoryFile file) {
		beginTransaction();
		file.setState(VisitState.SKIPPED);
		update(file);
		commitTransaction();
	}
	
	public List<RepositoryFile> findUnvisited() {
		return getEntityManager().createNamedQuery("FindRepositoryFileByState")
			.setParameter("state", VisitState.NOT_VISITED)
			.setMaxResults(100)
			.getResultList();
	}

}
