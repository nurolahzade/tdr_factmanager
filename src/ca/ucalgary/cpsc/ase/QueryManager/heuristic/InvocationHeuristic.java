package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.LinkedHashMap;

import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.MethodService;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class InvocationHeuristic extends DatabaseHeuristic {

	// the ones a match was found for in the repository
	protected Set<Integer> resolvedInvocations = new HashSet<Integer>();
	// the ones a match could not be found in the repository
	protected Set<QueryMethod> unresolvedInvocations = new HashSet<QueryMethod>();

	@Override
	public Map<Integer, ResultItem> match(Query q) {
				
		Map<Integer, ResultItem> results;
		
		resolveInvocations(q);
		
		if (resolvedInvocations.size() > 0) {
			// send query to Database
			TestMethodService service = new TestMethodService();
			List dbResults = service.matchInvocations(resolvedInvocations);
			
			results = parse(dbResults);			
		}
		else {
			results = new LinkedHashMap<Integer, ResultItem>();
		}
				
		return results;
	}

	protected void resolveInvocations(Query q) {
		// all method invocations in the user test
		List<QueryMethod> qInvocations = q.getInvocations();

		// try to match query methods to the ones in the repository
		for (QueryMethod qMethod : qInvocations) {
			categorize(qMethod);
		}
	}

	protected void categorize(QueryMethod qMethod) {
		Method method = bestMatchInRepository(qMethod);
		if (method != null) {
			resolvedInvocations.add(method.getId());
		}
		else {
			unresolvedInvocations.add(qMethod);
		}
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
	
}
