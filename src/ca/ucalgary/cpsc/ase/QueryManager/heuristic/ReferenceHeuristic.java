package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Reference;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Heuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class ReferenceHeuristic implements Heuristic {

	@Override
	public List<ResultItem> match(Query q) {
		// all references in user test
		List<QueryReference> qReferences = q.getReferences();
		// FQN of all references in user test
		Set<String> fqns = new HashSet<String>();
		
		// extract fqns from query
		for (QueryReference qReference : qReferences) {
			fqns.add(qReference.getClazzFqn());
		}
		
		// find matching test methods
		TestMethodService service = new TestMethodService();
		List dbResults = service.matchReferences(fqns);

		// convert results
		List<ResultItem> results = new ArrayList<ResultItem>();
		for (int i = 0; i < dbResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] dbResult = (Object[]) dbResults.get(i);
			result.setTarget((TestMethod) dbResult[0]);
			result.setScore(((Long) dbResult[1]).doubleValue());
			results.add(result);
		}
		return results;
	}	
	

}
