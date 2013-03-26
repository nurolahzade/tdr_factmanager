package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.RepositoryFile;
import ca.ucalgary.cpsc.ase.common.entity.VisitState;
import ca.ucalgary.cpsc.ase.common.service.RepositoryFileServiceRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.RepositoryFileServiceLocal;

@Stateless(name="RepositoryFileService", mappedName=ServiceDirectory.REPOSITORY_FILE_SERVICE)
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<RepositoryFile> findUnvisited(int maxResults) {
		return getEntityManager().createNamedQuery("FindRepositoryFileByState")
			.setParameter("state", VisitState.NOT_VISITED)
			.setMaxResults(maxResults)
			.getResultList();
	}

}
