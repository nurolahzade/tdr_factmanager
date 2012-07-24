package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethodHasAssertion;

public class TestMethodHasAssertionService extends AbstractService<TestMethodHasAssertion> {

	private static Logger logger = Logger.getLogger(TestMethodHasAssertion.class);

	public TestMethodHasAssertionService() {
		super(TestMethodHasAssertion.class);
	}
	
	public TestMethodHasAssertion find(TestMethod testMethod, Assertion assertion) {
		try {
			return (TestMethodHasAssertion) getEntityManager().createNamedQuery("findTestMethodHasAssertion")
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
		TestMethodHasAssertion tmha = new TestMethodHasAssertion();
		tmha.setTestMethod(testMethod);
		tmha.setAssertion(assertion);
		tmha.setPosition(position);
		create(tmha);
		commitTransaction();
		return tmha;
	}

}
