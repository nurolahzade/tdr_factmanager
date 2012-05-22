package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.MethodService;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Heuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class InvocationHeuristic implements Heuristic {

	@Override
	public List<ResultItem> match(Query q) {
		
		// all method invocations in the user test
		List<QueryMethod> qInvocations = q.getInvocations();
		// the ones a match was found for in the repository
		List<Integer> resolvedInvocations = new ArrayList<Integer>();
		// the ones a match could not be found in the repository
		List<QueryMethod> unresolvedInvocations = new ArrayList<QueryMethod>();
		
		// try to match query methods to the ones in the repository
		for (QueryMethod qMethod : qInvocations) {

			Method method = bestMatchInRepository(qMethod);
			if (method != null) {
				resolvedInvocations.add(method.getId());
			}
			else {
				unresolvedInvocations.add(qMethod);
			}
		}
		
		// send query to Database
		TestMethodService service = new TestMethodService();
		List databaseResults = service.matchInvocations(resolvedInvocations);
		
		List<ResultItem> results = new ArrayList<ResultItem>();
		
		for (int i = 0; i < databaseResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] databaseResult = (Object[]) databaseResults.get(i);
			result.setTarget((TestMethod) databaseResult[0]);
			result.setScore(((Long) databaseResult[1]).doubleValue());
			results.add(result);
		}
		
		// send query to Solr
		
		// combine results
		
		return results;
	}

	private Method bestMatchInRepository(QueryMethod qMethod) {
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

}
