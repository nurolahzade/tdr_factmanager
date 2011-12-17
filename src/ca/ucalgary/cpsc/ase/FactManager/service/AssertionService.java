package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionType;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class AssertionService extends AbstractService<Assertion> {

	private static Logger logger = Logger.getLogger(AssertionService.class);

	public AssertionService() {
		super(Assertion.class);
	}

	public Assertion create(AssertionType type, TestMethod testMethod) {
		beginTransaction();
		Assertion assertion = new Assertion();
		assertion.setType(type);
		Set<TestMethod> testMethods = new HashSet<TestMethod>();
		testMethods.add(testMethod);
		assertion.setTestMethods(testMethods);
		create(assertion);
		commitTransaction();
		return assertion;
	}
	
	public Assertion find(AssertionType type) {
		try {
			return (Assertion) getEntityManager().createNamedQuery("findByType").
				setParameter("type", type).getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;			
		}
	}
	
	public Assertion createOrGet(AssertionType type, TestMethod testMethod) {
		Assertion assertion = find(type);
		if (assertion == null) {
			assertion = create(type, testMethod);
		}
		else {
			addTestMethod(assertion, testMethod);
		}
		return assertion;
	}

	private void addTestMethod(Assertion assertion, TestMethod testMethod) {
		Set<TestMethod> testMethods = assertion.getTestMethods();
		if (testMethods != null) {
			if (testMethods.contains(testMethods))
				return;
		}
		else {
			testMethods = new HashSet<TestMethod>();
			assertion.setTestMethods(testMethods);
		}
		beginTransaction();
		testMethods.add(testMethod);
		update(assertion);
		commitTransaction();
	}

}
