package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Project;
import ca.ucalgary.cpsc.ase.common.entity.SourceFile;

@Stateless(name="SourceFileService", mappedName="ejb/SourceFileService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SourceFileService extends AbstractService<SourceFile> implements SourceFileServiceLocal {

	public SourceFileService() {
		super(SourceFile.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SourceFile create(Project project, String path) {
		SourceFile source = new SourceFile();
		source.setProject(project);
		source.setPath(path);
		create(source);

		return source;
	}

}
