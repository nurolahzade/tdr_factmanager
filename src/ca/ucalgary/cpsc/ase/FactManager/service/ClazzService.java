package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Pakage;
import ca.ucalgary.cpsc.ase.FactManager.entity.SourceFile;
import ca.ucalgary.cpsc.ase.FactManager.entity.ObjectType;

public class ClazzService extends AbstractService<Clazz> {

	private List<ObjectType> types;

	private static Logger logger = Logger.getLogger(ClazzService.class);

	public ClazzService() {
		super(Clazz.class);
		types = new ArrayList<ObjectType>();
		types.add(ObjectType.JUNIT3);
		types.add(ObjectType.JUNIT4);
	}
	
	public Clazz create(String className, String packageName, String fqn, SourceFile source, ObjectType type) {
		beginTransaction();
		Clazz clazz = new Clazz();
		clazz.setClassName(className);
		clazz.setPackage(createPakage(packageName));
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
		else {
			if ((clazz.getSourceFile() == null) && (source != null)) {
				updateSourceFile(clazz, source);
			}
			if (clazz.getType() == ObjectType.CLASS && (type == ObjectType.JUNIT3 || type == ObjectType.JUNIT4)) {
				updateType(clazz, type);
			}			
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

	protected void updateType(Clazz clazz, ObjectType type) {
		beginTransaction();
		clazz.setType(type);
		update(clazz);
		commitTransaction();
	}
	
	protected Pakage createPakage(String name) {
		Pakage pakage = null;
		if (name != null && !name.isEmpty()) {
			PakageService service = new PakageService();
			pakage = service.createOrGet(name);			
		}
		return pakage;
	}
	
	public List matchReferences(Set<String> fqns) {
		return getEntityManager().createNamedQuery("MatchReference").
				setParameter("types", types).
				setParameter("fqns", fqns).
				getResultList();
	}
	
	public List matchInvocations(Set<Integer> methods) {		
		return getEntityManager().createNamedQuery("MatchSimpleCall").
				setParameter("types", types).
				setParameter("list", methods).
				getResultList();
	}
	
	public List matchAssertions(Set<Integer> assertions) {
		return getEntityManager().createNamedQuery("MatchAssertion").
				setParameter("types", types).
				setParameter("list", assertions).
				getResultList();
	}
	
	public List matchAssertionParameters(Set<Integer> methods) {
		return getEntityManager().createNamedQuery("MatchAssertionParameter").
				setParameter("types", types).
				setParameter("list", methods).
				getResultList();
	}
	
	public Long getInvocationsCount(Clazz c) {
		return (Long) getEntityManager().createNamedQuery("TotalMethodsInTestClass").
				setParameter("clazz", c).
				getSingleResult();
	}
	
}
