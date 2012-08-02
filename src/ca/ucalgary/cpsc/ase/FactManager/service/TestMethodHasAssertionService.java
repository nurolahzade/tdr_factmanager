package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethodHasAssertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethodHasAssertionPK;

public class TestMethodHasAssertionService extends AbstractService<TestMethodHasAssertion> {

	private static Logger logger = Logger.getLogger(TestMethodHasAssertion.class);

	public TestMethodHasAssertionService() {
		super(TestMethodHasAssertion.class);
	}
	
	public TestMethodHasAssertion find(TestMethod testMethod, Assertion assertion) {
		try {
			return (TestMethodHasAssertion) getEntityManager().createNamedQuery("findTestMethodAssertion")
				.setParameter("testMethod", testMethod)
				.setParameter("assertion", assertion)
				.getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}		
	}
	
	public TestMethodHasAssertion createOrGet(TestMethod testMethod, Assertion assertion, Position position) {
		TestMethodHasAssertion testMethodHasAssertion = find(testMethod, assertion);
		if (testMethodHasAssertion == null) {
			testMethodHasAssertion = create(testMethod, assertion, position);
		}
		return testMethodHasAssertion;
	}

	public TestMethodHasAssertion create(TestMethod testMethod,
			Assertion assertion, Position position) {
		beginTransaction();
		TestMethodHasAssertionPK pk = new TestMethodHasAssertionPK();
		pk.setTestMethodId(testMethod.getId());
		pk.setAssertionId(assertion.getId());
		TestMethodHasAssertion tmha = new TestMethodHasAssertion();
		tmha.setId(pk);
		tmha.setTestMethod(testMethod);
		tmha.setAssertion(assertion);
		tmha.setPosition(position);
		create(tmha);
		commitTransaction();
		return tmha;
	}

}
