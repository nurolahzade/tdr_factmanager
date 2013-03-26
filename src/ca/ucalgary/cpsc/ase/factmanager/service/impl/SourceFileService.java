package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Project;
import ca.ucalgary.cpsc.ase.common.entity.SourceFile;
import ca.ucalgary.cpsc.ase.common.service.SourceFileServiceRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.SourceFileServiceLocal;

@Stateless(name="SourceFileService", mappedName=ServiceDirectory.SOURCE_FILE_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SourceFileService extends AbstractService<SourceFile> implements SourceFileServiceLocal, SourceFileServiceRemote {

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
