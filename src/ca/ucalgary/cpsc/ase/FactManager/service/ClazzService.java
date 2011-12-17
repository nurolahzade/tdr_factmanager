package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.SourceFile;
import ca.ucalgary.cpsc.ase.FactManager.entity.ObjectType;

public class ClazzService extends AbstractService<Clazz> {

	private static Logger logger = Logger.getLogger(ClazzService.class);

	public ClazzService() {
		super(Clazz.class);
	}
	
	public Clazz create(String className, String packageName, String fqn, SourceFile source, ObjectType type) {
		beginTransaction();
		Clazz clazz = new Clazz();
		clazz.setClassName(className);
		clazz.setPackageName(packageName);
		clazz.setFqn(fqn);
		clazz.setSourceFile(source);
		clazz.setType(type);
		create(clazz);
		commitTransaction();
		return clazz;
	}
	
	public Clazz createOrGet(String className, String packageName, String fqn, SourceFile source, ObjectType type) {
		Clazz clazz = find(fqn);
		if (clazz == null) {
			clazz = create(className, packageName, fqn, source, type);			
		}
		else if ((clazz.getSourceFile() == null) && (source != null)) {
			updateSourceFile(clazz, source);
		}
		return clazz;
	}
	
	public Clazz find(String fqn) {
		try {
			return (Clazz) getEntityManager().createNamedQuery("findByFQN").setParameter("fqn", fqn).getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}

	protected void updateSourceFile(Clazz clazz, SourceFile source) {
		beginTransaction();
		clazz.setSourceFile(source);
		update(clazz);
		commitTransaction();
	}

}
