package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionType;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertionParameter;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class HeuristicManagerTest {

	public static void main(String[] args) throws Exception {
		HeuristicManagerTest test = new HeuristicManagerTest();
		test.testVotingHeuristicManager();
	}
	
	public void testVotingHeuristicManager() throws Exception {
		QueryReference reference = new QueryReference();
		reference.setClazzFqn("java.lang.String");
		reference.setDeclaringClazzFqn(null);
		reference.setName("str");
		
		List<QueryReference> references = new ArrayList<QueryReference>();
		references.add(reference);
		
		QueryMethod method = new QueryMethod();
		method.setClazzFqn("java.lang.String");
		method.setName("length");
		method.setReturnTypeFqn("int");
		method.setArguments(0);
		method.setConstructor(false);
		method.setHash(0);

		List<QueryMethod> invocations = new ArrayList<QueryMethod>();
		invocations.add(method);
		
		QueryAssertion assertion = new QueryAssertion();
		assertion.setType(AssertionType.ASSERT_EQUALS);

		List<QueryAssertion> assertions = new ArrayList<QueryAssertion>();
		assertions.add(assertion);
		
		List<QueryAssertionParameter> parameters = new ArrayList<QueryAssertionParameter>();
		
		Query q = new Query();
		q.setReferences(references);
		q.setInvocations(invocations);
		q.setAssertions(assertions);
		q.setParameters(parameters);
		
		VotingHeuristicManager manager = new VotingHeuristicManager();
		Map<Integer, Double> results = manager.match(q);
		
		print(results);
	}

	private void print(Map<Integer, Double> results) {
		for (Integer id : results.keySet()) {
			System.out.println(id + " " + results.get(id));
		}
	}

}
