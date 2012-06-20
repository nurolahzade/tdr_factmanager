package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.entity.RepositoryFile;

public class RepositoryFileService extends AbstractService<RepositoryFile> {

	public RepositoryFileService() {
		super(RepositoryFile.class);
	}
	
	public RepositoryFile create(String path) {
		beginTransaction();
		RepositoryFile file = new RepositoryFile();
		file.setPath(path);
		file.setVisited(false);
		create(file);
		commitTransaction();
		
		return file;
	}
	
	public void visit(RepositoryFile file) {
		beginTransaction();
		file.setVisited(true);
		update(file);
		commitTransaction();
	}
	
	public List<RepositoryFile> findUnvisited() {
		return getEntityManager().createNamedQuery("FindUnvisited")
			.setMaxResults(100)
			.getResultList();
	}

}
