import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.InvocationHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.ReferenceHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test test = new Test();
		test.testReferenceHeuristic();
	}
	
	public void testReferenceHeuristic() {
		QueryReference r1 = new QueryReference();
		r1.setClazzFqn("java.lang.String");
		r1.setDeclaringClazzFqn(null);
		r1.setName("str");
		
		QueryReference r2 = new QueryReference();
		r2.setClazzFqn("int");
		r2.setDeclaringClazzFqn(null);
		r2.setName("i");
		
		List<QueryReference> references = new ArrayList<QueryReference>();
		references.add(r1);
		references.add(r2);
		
		Query q = new Query();
		q.setReferences(references);
		
		ReferenceHeuristic heuristic = new ReferenceHeuristic();
		List<ResultItem> results = heuristic.match(q);
		
		print(results);
	}
	
	public void testInvocationHeuristic() {
		QueryMethod m1 = new QueryMethod();
		m1.setClazzFqn("java.lang.String");
		m1.setName("length");
		m1.setReturnTypeFqn("int");
		m1.setArguments(0);
		m1.setConstructor(false);
		m1.setHash(0);
		
		QueryMethod m2 = new QueryMethod();
		m2.setClazzFqn("java.net.URL");
		m2.setName("toString");
		m2.setReturnTypeFqn("java.lang.String");
		m2.setArguments(0);
		m2.setConstructor(false);
		m2.setHash(0);
		
		List<QueryMethod> invocations = new ArrayList<QueryMethod>();
		invocations.add(m1);
		invocations.add(m2);
		
		Query q = new Query();
		q.setInvocations(invocations);
		
		InvocationHeuristic heuristic = new InvocationHeuristic();
		List<ResultItem> results = heuristic.match(q);
		
		print(results);
	}
	
	private void print(List<ResultItem> results) {
		for (ResultItem r : results) {
			System.out.println(r.getTarget().getId() + " " + r.getTarget().getName() + " " + r.getScore());
		}		
	}

}
