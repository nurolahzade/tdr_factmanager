package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Pakage;
import ca.ucalgary.cpsc.ase.common.entity.SourceFile;
import ca.ucalgary.cpsc.ase.common.entity.ObjectType;
import ca.ucalgary.cpsc.ase.common.service.ClazzServiceRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.PakageServiceLocal;

@Stateless(name="ClazzService", mappedName=ServiceDirectory.CLAZZ_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClazzService extends AbstractService<Clazz> implements ClazzServiceLocal, ClazzServiceRemote {

	@EJB(name="PakageService")
	private PakageServiceLocal pakageService;
	private List<ObjectType> types;

	private static Logger logger = Logger.getLogger(ClazzService.class);

	public ClazzService() {
		super(Clazz.class);
		types = new ArrayList<ObjectType>();
		types.add(ObjectType.JUNIT3);
		types.add(ObjectType.JUNIT4);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Clazz create(String className, String packageName, String fqn, SourceFile source, ObjectType type) {
		Clazz clazz = new Clazz();
		clazz.setClassName(className);
		clazz.setPackage(createPakage(packageName));
		clazz.setFqn(fqn);
		clazz.setSourceFile(source);
		clazz.setType(type);
		create(clazz);

		return clazz;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Clazz find(String fqn) {
		try {
			return (Clazz) getEntityManager().createNamedQuery("findByFQN").setParameter("fqn", fqn).getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected void updateSourceFile(Clazz clazz, SourceFile source) {
		clazz.setSourceFile(source);
		update(clazz);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected void updateType(Clazz clazz, ObjectType type) {
		clazz.setType(type);
		update(clazz);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected Pakage createPakage(String name) {
		Pakage pakage = null;
		if (name != null && !name.isEmpty()) {
			pakage = pakageService.createOrGet(name);			
		}
		return pakage;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchReferences(Set<String> fqns) {
		return getEntityManager().createNamedQuery("MatchReference").
				setParameter("types", types).
				setParameter("fqns", fqns).
				getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchInvocations(Set<Integer> methods) {		
		return getEntityManager().createNamedQuery("MatchSimpleCall").
				setParameter("types", types).
				setParameter("list", methods).
				getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchAssertions(Set<Integer> assertions) {
		return getEntityManager().createNamedQuery("MatchAssertion").
				setParameter("types", types).
				setParameter("list", assertions).
				getResultList();
	}
	
//	public List matchAssertionParameters(Set<Integer> methods) {
//		return getEntityManager().createNamedQuery("MatchAssertionParameter").
//				setParameter("types", types).
//				setParameter("list", methods).
//				getResultList();
//	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Long getInvocationsCount(Clazz c) {
		return (Long) getEntityManager().createNamedQuery("TotalMethodsInTestClass").
				setParameter("clazz", c).
				getSingleResult();
	}
	
}
