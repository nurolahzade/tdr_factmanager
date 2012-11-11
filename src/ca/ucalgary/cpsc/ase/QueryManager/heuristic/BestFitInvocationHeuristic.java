package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class BestFitInvocationHeuristic extends InvocationHeuristic {

	@Override
	public String getName() {
		return "BFI";
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		TestMethodService service = new TestMethodService();
		return service.getInvocationsCount(item.getTarget());
	}

}
