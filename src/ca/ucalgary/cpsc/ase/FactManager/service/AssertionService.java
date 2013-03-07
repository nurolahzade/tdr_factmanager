package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.AssertionType;

@Stateless(name="AssertionService", mappedName="ejb/AssertionService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AssertionService extends AbstractService<Assertion> implements AssertionServiceLocal {

	private static Logger logger = Logger.getLogger(AssertionService.class);

	public AssertionService() {
		super(Assertion.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Assertion create(AssertionType type) {
		Assertion assertion = new Assertion();
		assertion.setType(type);
		create(assertion);
		return assertion;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Assertion find(AssertionType type) {
		try {
			return (Assertion) getEntityManager().createNamedQuery("findByType").
				setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			logger.debug(e.getMessage());
			return null;			
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Assertion createOrGet(AssertionType type) {
		Assertion assertion = find(type);
		if (assertion == null) {
			assertion = create(type);
		}
		return assertion;
	}

//	private void addTestMethod(Assertion assertion, TestMethod testMethod, Position position) {
//		MethodInvocationService service = new MethodInvocationService();
//		service.create(testMethod, null, assertion, position);
//	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List<Assertion> getMatchingAssertions(Integer id, Set<Integer> assertions) {
		return getEntityManager().createNamedQuery("FindMatchingAssertions").
				setParameter("id", id).
				setParameter("list", assertions).
				getResultList();
	}
	
}
