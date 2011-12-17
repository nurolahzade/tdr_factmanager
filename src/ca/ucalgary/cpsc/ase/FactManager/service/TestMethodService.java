package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.Date;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class TestMethodService extends AbstractService<TestMethod> {

	public TestMethodService() {
		super(TestMethod.class);
	}
	
	public TestMethod create(String name, Clazz testClass) {
		beginTransaction();
		TestMethod testMethod = new TestMethod();
		testMethod.setName(name);
		testMethod.setLastModified(new Date());
		testMethod.setClazz(testClass);
		create(testMethod);
		commitTransaction();
		return testMethod;
	}
	
}
