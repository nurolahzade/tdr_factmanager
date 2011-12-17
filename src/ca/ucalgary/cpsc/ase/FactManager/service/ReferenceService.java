package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Reference;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class ReferenceService extends AbstractService<Reference> {

	private static Logger logger = Logger.getLogger(ReferenceService.class);

	public ReferenceService() {
		super(Reference.class);
	}
	
	public Reference create(String name, Clazz clazz, Clazz declaringClazz, TestMethod testMethod) {
		beginTransaction();
		Reference reference = new Reference();
		reference.setName(name);
		reference.setClazz(clazz);
		reference.setDeclaringClazz(declaringClazz);
		reference.setTestMethod(testMethod);
		create(reference);
		commitTransaction();
		return reference;
	}

	public Reference createOrGet(String name, Clazz clazz, Clazz declaringClazz, TestMethod testMethod) {
		Reference reference = find(name, clazz, declaringClazz, testMethod);
		if (reference == null) {
			reference = create(name, clazz, declaringClazz, testMethod);
		}
		return reference;
	}
	
	public Reference find(String name, Clazz clazz, Clazz declaringClazz, TestMethod testMethod) {
		try {
			return (Reference) getEntityManager().createNamedQuery("FindReference").
				setParameter("name", name).
				setParameter("clazz", clazz).
				setParameter("declaring", declaringClazz).
				setParameter("method", testMethod).
				getSingleResult();
			} catch (Exception e) {
				logger.debug(e.getMessage());
				return null;
		}
	}

}
