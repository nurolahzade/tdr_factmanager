package ca.ucalgary.cpsc.ase.FactManager.service;

import ca.ucalgary.cpsc.ase.FactManager.entity.Project;
import ca.ucalgary.cpsc.ase.FactManager.entity.SourceFile;

public class SourceFileService extends AbstractService<SourceFile> {

	public SourceFileService() {
		super(SourceFile.class);
	}
	
	public SourceFile create(Project project, String path) {
		beginTransaction();
		SourceFile source = new SourceFile();
		source.setProject(project);
		source.setPath(path);
		create(source);
		commitTransaction();
		return source;
	}

}
