package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertionParameter;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class AssertionParameterHeuristic extends InvocationHeuristic {

//	@Override
//	public Map<Integer, ResultItem> match(Query q) {
//		
//		Map<Integer, ResultItem> results;
//		
//		resolveAssertionParameters(q);
//		
//		if (resolvedInvocations.size() > 0) {		
//			TestMethodService service = new TestMethodService();
//			List dbResults = service.matchAssertionParameters(resolvedInvocations);
//		
//			results = parse(dbResults);
//		}
//		else {
//			results = new LinkedHashMap<Integer, ResultItem>();
//		}
//		return results;
//	}

	@Override
	protected Set resolve(Query q) {
		Set<Integer> resolvedInvocations = new HashSet<Integer>();
		List<QueryAssertionParameter> parameters = q.getParameters();
		if (parameters == null)
			return resolvedInvocations;
				
		for (int i = 0; i < parameters.size(); ++i) {
			QueryMethod qMethod = parameters.get(i).getMethod();
			Method method = bestMatchInRepository(qMethod);
			if (method != null) {
				resolvedInvocations.add(method.getId());
			}
		}
		return resolvedInvocations;
	}

	@Override
	public String getName() {
		return "AP";
	}
	
	@Override
	protected List retrieve(Set resolved) {
		TestMethodService service = new TestMethodService();
		return service.matchAssertionParameters(resolved);
	}

	@Override
	protected void normalize(Query q, Map<Integer, ResultItem> results) {
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / q.getParameters().size());
		}		
	}
}
