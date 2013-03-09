package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import ca.ucalgary.cpsc.ase.FactManager.service.ClazzService;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class BestFitInvocationHeuristic extends InvocationHeuristic {

	@Override
	public String getName() {
		return "BFI";
	}

	@Override
	public String getFullName() {
		return "Best Fit Invocations";
	}	
	
	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		ClazzService service = new ClazzService();
		return service.getInvocationsCount(item.getTarget());
	}

}
