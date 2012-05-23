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
import ca.ucalgary.cpsc.ase.QueryManager.Heuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class InvocationHeuristic implements Heuristic {

	// the ones a match was found for in the repository
	protected Set<Integer> resolvedInvocations = new HashSet<Integer>();
	// the ones a match could not be found in the repository
	protected Set<QueryMethod> unresolvedInvocations = new HashSet<QueryMethod>();

	@Override
	public Map<Integer, ResultItem> match(Query q) {
				
		resolveInvocations(q);
		
		// send query to Database
		TestMethodService service = new TestMethodService();
		List dbResults = service.matchInvocations(resolvedInvocations);
		
		Map<Integer, ResultItem> results = parse(dbResults);
		// map score values to the range 0..1
		normalize(results, q.getInvocations().size());
		
		// send query to Solr
		
		// combine results
		
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
	
	protected void normalize(Map<Integer, ResultItem> results, int factor) {
		if (factor == 0) return;
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / factor);
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
	
	protected Map<Integer, ResultItem> parse(List rawResults) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		for (int i = 0; i < rawResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] databaseResult = (Object[]) rawResults.get(i);
			TestMethod tm = (TestMethod) databaseResult[0];
			result.setTarget(tm);
			result.setScore(((Long) databaseResult[1]).doubleValue());
			results.put(tm.getId(), result);
		}
		return results;
	}

}
