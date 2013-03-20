package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.MethodServiceLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class InvocationHeuristic extends DatabaseHeuristic {

	@EJB private MethodServiceLocal methodService;
	@EJB private ClazzServiceLocal clazzService; 
	
	protected Set resolve(Query q) {
		Set<Integer> resolvedInvocations = new HashSet<Integer>();
		// all method invocations in the user test
		List<QueryMethod> qInvocations = q.getInvocations();

		// try to match query methods to the ones in the repository
		for (QueryMethod qMethod : qInvocations) {
			Method method = bestMatchInRepository(qMethod);
			if (method != null) {
				resolvedInvocations.add(method.getId());
				qMethod.setResolved(true);
			}
			else {
				qMethod.setResolved(false);
			}
		}
		return resolvedInvocations;
	}

	protected Method bestMatchInRepository(QueryMethod qMethod) {
		List<Method> methods = methodService.find(qMethod.getName(), qMethod.getClazzFqn(), qMethod.getArguments());
		if (methods == null || methods.size() == 0) {
			return null;
		}
		else if (methods.size() == 1) {
			return methods.get(0);
		}
		else {
			// todo - verify which of the returned results better matches return type and argument hash 
			return methods.get(0);
		}
	}

	@Override
	public String getName() {
		return "I";
	}

	@Override
	public String getFullName() {
		return "Invocations";
	}

	@Override
	protected List retrieve(Set resolved) {
		return clazzService.matchInvocations(resolved);
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getInvocations().size();
	}
	
	@Override
	public List<String> getMatchingItems(Integer id, Query q) {
		return entityToString(methodService.getMatchingInvocations(id, resolve(q)));
	}

}
