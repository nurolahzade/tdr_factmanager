package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.ObjectType;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class TestMethodService extends AbstractService<TestMethod> {

	private List<ObjectType> types;
	
	public TestMethodService() {
		super(TestMethod.class);
		types.add(ObjectType.JUNIT3);
		types.add(ObjectType.JUNIT4);
	}
	
	public TestMethod create(String name, Clazz testClass, Position position) {
		beginTransaction();
		TestMethod testMethod = new TestMethod();
		testMethod.setName(name);
		testMethod.setLastModified(new Date());
		testMethod.setClazz(testClass);
		testMethod.setPosition(position);
		create(testMethod);
		commitTransaction();
		return testMethod;
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
