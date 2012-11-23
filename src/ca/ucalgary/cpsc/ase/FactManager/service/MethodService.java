package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionOnMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethodCallsMethod;

public class MethodService extends AbstractService<Method> {

	private static Logger logger = Logger.getLogger(MethodService.class);

	public MethodService() {
		super(Method.class);
	}
	
	public Method create(String name, Clazz clazz, Clazz returnType, boolean isConstructor, 
			int arguments, int hash) {
		beginTransaction();
		Method method = new Method();
		method.setName(name);
		method.setClazz(clazz);
		method.setReturnClazz(returnType);
		method.setConstructor(isConstructor);
		method.setArguments(arguments);
		method.setHash(hash);
		create(method);
		commitTransaction();
		return method;
	}

	public Method createOrGet(String name, Clazz clazz, Clazz returnType, boolean isConstructor, 
			int arguments, int hash, TestMethod testMethod, Assertion assertion, Position position) {
		Method method = find (name, clazz, returnType, arguments, hash);
		if (method == null) {
			method = create(name, clazz, returnType, isConstructor, arguments, hash);
		}
		if (testMethod != null) {
			addTestMethod(method, testMethod, position);			
			if (assertion != null) {
				addAssertionOnMethod(method, assertion, testMethod);
			}
		}
		return method;
	}
	
	@SuppressWarnings("unchecked")
	public List<Method> find(String name, String fqn, int arguments) {
		return getEntityManager().createNamedQuery("FindMethodByFQN").
				setParameter("name", name).
				setParameter("fqn", fqn).
				setParameter("arguments", arguments).
				getResultList();
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

	protected void addTestMethod(Method method, TestMethod testMethod, Position position) {	
		TestMethodCallsMethodService service = new TestMethodCallsMethodService();
		Set<TestMethodCallsMethod> testMethods = method.getTestMethods();
		if (testMethods != null) {
			if (service.find(testMethod, method) != null)
				return;			
		}
		else {
			testMethods = new HashSet<TestMethodCallsMethod>();
			method.setTestMethods(testMethods);
		}
		beginTransaction();
		testMethods.add(service.create(testMethod, method, position));
		update(method);
		commitTransaction();
	}
	
	protected AssertionOnMethod addAssertionOnMethod(Method method, Assertion assertion, TestMethod testMethod) {
		AssertionOnMethodService service = new AssertionOnMethodService();
		return service.createOrGet(assertion, method, testMethod);
	}
	
	public List<Method> getMatchingInvocations(Integer id, Set<Integer> methods) {		
		return getEntityManager().createNamedQuery("FindMatchingCalls").
				setParameter("id", id).
				setParameter("list", methods).
				getResultList();
	}
		
}
