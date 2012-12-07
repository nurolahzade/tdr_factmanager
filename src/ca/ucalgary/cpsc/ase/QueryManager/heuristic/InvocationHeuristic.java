package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.service.ClazzService;
import ca.ucalgary.cpsc.ase.FactManager.service.MethodService;
import ca.ucalgary.cpsc.ase.QueryManager.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class InvocationHeuristic extends DatabaseHeuristic {

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
		MethodService service = new MethodService();
		List<Method> methods = service.find(qMethod.getName(), qMethod.getClazzFqn(), qMethod.getArguments());
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
		ClazzService service = new ClazzService();
		return service.matchInvocations(resolved);
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getInvocations().size();
	}
	
	@Override
	public List<Method> getMatchingItems(Integer id, Query q) {
		MethodService service = new MethodService();
		return service.getMatchingInvocations(id, resolve(q));
	}

}
