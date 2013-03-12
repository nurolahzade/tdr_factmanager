package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.service.MethodServiceRemote;
import ca.ucalgary.cpsc.ase.common.service.ServiceDirectory;

@Stateless(name="MethodService", mappedName=ServiceDirectory.METHOD_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MethodService extends AbstractService<Method> implements MethodServiceLocal, MethodServiceRemote {

	private static Logger logger = Logger.getLogger(MethodService.class);

	@EJB(name="ArgumentService")
	private ArgumentServiceLocal argumentService;
	
	public MethodService() {
		super(Method.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Method create(String name, Clazz clazz, Clazz returnType, boolean isConstructor, 
			List<Clazz> arguments, int hash) {
		Method method = new Method();
		method.setName(name);
		method.setClazz(clazz);
		method.setReturnClazz(returnType);
		method.setConstructor(isConstructor);
		method.setHash(hash);
		create(method);

		argumentService.create(method, arguments);
		
		return method;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Method createOrGet(String name, Clazz clazz, Clazz returnType, boolean isConstructor, 
			List<Clazz> arguments, int hash) {
		Method method = find (name, clazz, returnType, arguments, hash);
		if (method == null) {
			method = create(name, clazz, returnType, isConstructor, arguments, hash);
		}
		return method;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<Method> find(String name, String fqn, List<String> arguments) {
		return getEntityManager().createNamedQuery("FindMethodByFQN").
				setParameter("name", name).
				setParameter("fqn", fqn).
				setParameter("arguments", arguments.size()).
				getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	protected Method find(String name, Clazz clazz, Clazz returnType, List<Clazz> arguments, int hash) {
		try {
			return (Method) getEntityManager().createNamedQuery("FindMethod").
				setParameter("name", name).
				setParameter("clazz", clazz).
				setParameter("returnClazz", returnType).
				setParameter("arguments", arguments.size()).
				setParameter("hash", hash).
				getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<Method> getMatchingInvocations(Integer id, Set<Integer> methods) {		
		return getEntityManager().createNamedQuery("FindMatchingCalls").
				setParameter("id", id).
				setParameter("list", methods).
				getResultList();
	}
		
}
