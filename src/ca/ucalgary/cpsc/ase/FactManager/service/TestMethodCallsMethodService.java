package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethodCallsMethod;

public class TestMethodCallsMethodService extends AbstractService<TestMethodCallsMethod> {

	private static Logger logger = Logger.getLogger(TestMethodCallsMethod.class);

	public TestMethodCallsMethodService() {
		super(TestMethodCallsMethod.class);
	}
	
	public TestMethodCallsMethod find(TestMethod testMethod, Method method) {
		try {
			return (TestMethodCallsMethod) getEntityManager().createNamedQuery("findTestMethodCallsMethod")
				.setParameter("testMethod", testMethod)
				.setParameter("method", method)
				.getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}				
	}
	
	public TestMethodCallsMethod createOrGet(TestMethod testMethod, Method method, Position position) {
		TestMethodCallsMethod tmcm = find(testMethod, method);
		if (tmcm == null) {
			tmcm = create(testMethod, method, position);
		}
		return tmcm;
	}

	public TestMethodCallsMethod create(TestMethod testMethod,
			Method method, Position position) {
		beginTransaction();
		TestMethodCallsMethod tmcm = new TestMethodCallsMethod();
		tmcm.setTestMethod(testMethod);
		tmcm.setMethod(method);
		tmcm.setPosition(position);
		create(tmcm);
		commitTransaction();
		return tmcm;
	}
	

}
