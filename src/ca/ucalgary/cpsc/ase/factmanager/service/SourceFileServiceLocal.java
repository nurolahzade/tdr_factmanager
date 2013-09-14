package ca.ucalgary.cpsc.ase.factmanager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Project;
import ca.ucalgary.cpsc.ase.common.entity.SourceFile;

@Local
public interface SourceFileServiceLocal extends ServiceFacade<SourceFile> {

	public SourceFile create(Project project, String path);

}