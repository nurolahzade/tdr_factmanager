import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.InvocationHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test test = new Test();
		test.testInvocationHeuristic();
	}
	
	public void testMatchReferences() {
		TestMethodService service = new TestMethodService();
		List<String> fqns = new ArrayList<String>();
		fqns.add("java.lang.String");
		fqns.add("int");
		List<Object[]> results = service.matchReferences(fqns);
		for (Object[] item : results) {
			System.out.print(item[0]);
			System.out.print(",");
			System.out.println(item[1]);
		}		
	}
	
	public void testInvocationHeuristic() {
		QueryMethod m1 = new QueryMethod();
		m1.setClazzFqn("java.lang.String");
		m1.setName("length");
		m1.setReturnTypeFqn("int");
		m1.setArguments(0);
		m1.setConstructor(false);
		m1.setHash(0);
		
//		QueryMethod m2 = new QueryMethod();
//		m2.setClazzFqn("java.util.List");
//		m2.setName("size");
//		m2.setReturnTypeFqn("int");
//		m2.setArguments(0);
//		m2.setConstructor(false);
//		m2.setHash(0);
		
		List<QueryMethod> invocations = new ArrayList<QueryMethod>();
		invocations.add(m1);
//		invocations.add(m2);
		
		Query q = new Query();
		q.setInvocations(invocations);
		
		InvocationHeuristic heuristic = new InvocationHeuristic();
		List<ResultItem> results = heuristic.match(q);
		
		for (ResultItem r : results) {
			System.out.print(r.getTarget().getId() + " " + r.getTarget().getName() + " " + r.getScore());
		}
		
	}

}
