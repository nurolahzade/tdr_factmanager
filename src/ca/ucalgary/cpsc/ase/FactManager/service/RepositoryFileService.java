package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.RepositoryFile;
import ca.ucalgary.cpsc.ase.common.entity.VisitState;

@Stateless(name="RepositoryFileService", mappedName="ejb/RepositoryFileService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RepositoryFileService extends AbstractService<RepositoryFile> implements RepositoryFileServiceRemote, RepositoryFileServiceLocal {

	public RepositoryFileService() {
		super(RepositoryFile.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RepositoryFile create(String path) {
		RepositoryFile file = new RepositoryFile();
		file.setPath(path);
		file.setState(VisitState.NOT_VISITED);
		create(file);
		
		return file;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void visit(RepositoryFile file) {
		file.setState(VisitState.VISITED);
		update(file);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void skip(RepositoryFile file) {
		file.setState(VisitState.SKIPPED);
		update(file);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RepositoryFile> findUnvisited() {
		return getEntityManager().createNamedQuery("FindRepositoryFileByState")
			.setParameter("state", VisitState.NOT_VISITED)
			.setMaxResults(100)
			.getResultList();
	}

}
