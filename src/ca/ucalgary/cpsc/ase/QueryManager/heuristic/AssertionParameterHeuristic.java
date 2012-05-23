package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.List;
import java.util.Map;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertionParameter;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class AssertionParameterHeuristic extends InvocationHeuristic {

	@Override
	public Map<Integer, ResultItem> match(Query q) {
		
		resolveAssertionParameters(q);
		
		TestMethodService service = new TestMethodService();
		List dbResults = service.matchAssertionParameters(resolvedInvocations);
		
		Map<Integer, ResultItem> results = parse(dbResults);
		
		normalize(results, q.getParameters().size());
		
		return results;
	}

	private void resolveAssertionParameters(Query q) {
		List<QueryAssertionParameter> parameters = q.getParameters();
		
		for (int i = 0; i < parameters.size(); ++i) {
			QueryMethod qMethod = parameters.get(i).getMethod();
			categorize(qMethod);
		}		
	}

	
	
}
