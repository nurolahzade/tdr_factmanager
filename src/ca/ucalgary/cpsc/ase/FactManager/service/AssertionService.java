package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionType;
import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethodHasAssertion;

public class AssertionService extends AbstractService<Assertion> {

	private static Logger logger = Logger.getLogger(AssertionService.class);

	public AssertionService() {
		super(Assertion.class);
	}

	public Assertion create(AssertionType type) {
		beginTransaction();
		Assertion assertion = new Assertion();
		assertion.setType(type);
		create(assertion);
		commitTransaction();
		return assertion;
	}
	
	public Assertion find(AssertionType type) {
		try {
			return (Assertion) getEntityManager().createNamedQuery("findByType").
				setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			logger.debug(e.getMessage());
			return null;			
		}
	}
	
	public Assertion createOrGet(AssertionType type) {
		return create(type);
	}
	
	public Assertion createOrGet(AssertionType type, TestMethod testMethod, Position position) {
		Assertion assertion = find(type);
		if (assertion == null) {
			assertion = create(type);
		}
		if (testMethod != null) {
			addTestMethod(assertion, testMethod, position);				
		}
		return assertion;
	}

	private void addTestMethod(Assertion assertion, TestMethod testMethod, Position position) {
		TestMethodHasAssertionService service = new TestMethodHasAssertionService();
		Set<TestMethodHasAssertion> testMethods = assertion.getTestMethods();
		if (testMethods != null) {
			if (service.find(testMethod, assertion) != null)
				return;
		}
		else {
			testMethods = new HashSet<TestMethodHasAssertion>();
			assertion.setTestMethods(testMethods);
		}
		beginTransaction();
		testMethods.add(service.create(testMethod, assertion, position));
		update(assertion);
		commitTransaction();
	}

	public List<Assertion> getMatchingAssertions(Integer id, Set<Integer> assertions) {
		return getEntityManager().createNamedQuery("FindMatchingAssertions").
				setParameter("id", id).
				setParameter("list", assertions).
				getResultList();
	}
	
}
