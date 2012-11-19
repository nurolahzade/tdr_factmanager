package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.service.ClazzService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertionParameter;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class AssertionParameterHeuristic extends InvocationHeuristic {

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
		ClazzService service = new ClazzService();
		return service.matchAssertionParameters(resolved);
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getParameters().size();
	}
	
	
}
