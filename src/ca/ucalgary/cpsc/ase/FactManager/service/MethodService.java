package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class MethodService extends AbstractService<Method> {

	private static Logger logger = Logger.getLogger(MethodService.class);

	public MethodService() {
		super(Method.class);
	}
	
	public Method create(String name, Clazz clazz, Clazz returnType, boolean isConstructor, int arguments, int hash, TestMethod testMethod, Assertion assertion) {
		beginTransaction();
		Method invocation = new Method();
		invocation.setName(name);
		invocation.setClazz(clazz);
		invocation.setReturnClazz(returnType);
		invocation.setConstructor(isConstructor);
		invocation.setArguments(arguments);
		invocation.setHash(hash);
		Set<TestMethod> testMethods = new HashSet<TestMethod>();
		invocation.setTestMethods(testMethods);
		if (assertion != null) {
			Set<Assertion> assertions = new HashSet<Assertion>();
			assertions.add(assertion);
			invocation.setAssertions(assertions);			
		}
		create(invocation);
		commitTransaction();
		return invocation;
	}

	public Method createOrGet(String name, Clazz clazz, Clazz returnType, boolean isConstructor, int arguments, int hash, TestMethod testMethod, Assertion assertion) {
		Method method = find (name, clazz, returnType, arguments, hash);
		if (method == null) {
			method = create(name, clazz, returnType, isConstructor, arguments, hash, testMethod, assertion);
		}
		else {
			addTestMethod(method, testMethod);
			if (assertion != null) {
				addAssertion(method, assertion);				
			}
		}
		return method;
	}
	
	protected Method find(String name, Clazz clazz, Clazz returnType, int arguments, int hash) {
		try {
			return (Method) getEntityManager().createNamedQuery("FindMethod").
				setParameter("name", name).
				setParameter("clazz", clazz).
				setParameter("returnClazz", returnType).
				setParameter("arguments", arguments).
				setParameter("hash", hash).
				getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}

	protected void addTestMethod(Method method, TestMethod testMethod) {	
		Set<TestMethod> testMethods = method.getTestMethods();
		if (testMethods != null) {
			if (testMethods.contains(testMethod))
				return;			
		}
		else {
			testMethods = new HashSet<TestMethod>();
			method.setTestMethods(testMethods);
		}
		beginTransaction();
		testMethods.add(testMethod);
		update(method);
		commitTransaction();
	}
	
	protected void addAssertion(Method method, Assertion assertion) {
		Set<Assertion> assertions = method.getAssertions();
		if (assertions != null) {
			if (assertions.contains(method))
				return;
		}
		else {
			assertions = new HashSet<Assertion>();
			method.setAssertions(assertions);
		}
		beginTransaction();
		assertions.add(assertion);
		update(method);
		commitTransaction();
	}
	
	@SuppressWarnings("unchecked")
	public List<Method> find(String name, String fqn, int arguments) {
		return getEntityManager().createNamedQuery("FindMethodByFQN").
				setParameter("name", name).
				setParameter("fqn", fqn).
				setParameter("arguments", arguments).
				getResultList();
	}
	
}
