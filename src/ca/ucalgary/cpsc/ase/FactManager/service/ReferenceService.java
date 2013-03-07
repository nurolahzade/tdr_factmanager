package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.Reference;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

@Stateless(name="ReferenceService", mappedName="ejb/ReferenceService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceService extends AbstractService<Reference> implements ReferenceServiceLocal {

	private static Logger logger = Logger.getLogger(ReferenceService.class);

	public ReferenceService() {
		super(Reference.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Reference create(String name, Clazz clazz, Clazz declaringClazz, TestMethod testMethod, Position position) {
		Reference reference = new Reference();
		reference.setName(name);
		reference.setClazz(clazz);
		reference.setDeclaringClazz(declaringClazz);
		reference.setTestMethod(testMethod);
		reference.setPosition(position);
		create(reference);

		return reference;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Reference createOrGet(String name, Clazz clazz, Clazz declaringClazz, TestMethod testMethod, Position position) {
		Reference reference = find(name, clazz, declaringClazz, testMethod);
		if (reference == null) {
			reference = create(name, clazz, declaringClazz, testMethod, position);
		}
		return reference;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Reference find(String name, Clazz clazz, Clazz declaringClazz, TestMethod testMethod) {
		try {
			Query q;
			if (declaringClazz != null) {
				q = getEntityManager().createNamedQuery("FindReference");
				q.setParameter("declaring", declaringClazz);
			}
			else {
				q = getEntityManager().createNamedQuery("FindReferenceNullDeclaringClazz");
			}

			return (Reference) q.setParameter("name", name).
					setParameter("clazz", clazz).
					setParameter("method", testMethod).
					getSingleResult();				
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List<Reference> getMatchingReferences(Integer id, Set<String> fqns) {
		return getEntityManager().createNamedQuery("FindMatchingReferences").
		setParameter("id", id).
		setParameter("fqns", fqns).
		getResultList();		
	}
		
}
