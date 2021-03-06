package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class TestMethodService extends AbstractService<TestMethod> {

	public TestMethodService() {
		super(TestMethod.class);
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
            setParameter("fqns", fqns).
            getResultList();
    }
     
    public List matchInvocations(Set<Integer> methods) {     
        return getEntityManager().createNamedQuery("MatchSimpleCall").
                setParameter("list", methods).
                getResultList();
    }
     
    public List matchAssertions(Set<Integer> assertions) {
        return getEntityManager().createNamedQuery("MatchAssertion").
                setParameter("list", assertions).
                getResultList();
    }
     
    public List matchAssertionParameters(Set<Integer> methods) {
        return getEntityManager().createNamedQuery("MatchAssertionParameter").
                setParameter("list", methods).
                getResultList();
    }
     
    public Long getInvocationsCount(TestMethod tm) {
        return (Long) getEntityManager().createNamedQuery("TotalMethodsInTestMethods").
            setParameter("testMethod", tm).
            getSingleResult();
    }
    
}
