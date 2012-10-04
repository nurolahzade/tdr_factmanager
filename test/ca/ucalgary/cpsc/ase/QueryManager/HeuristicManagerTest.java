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
		QueryReference r1 = new QueryReference();
		r1.setClazzFqn("java.lang.String[]");
		r1.setName("webpage");
		
		QueryReference r2 = new QueryReference();
		r2.setClazzFqn("int");
		r2.setName("k");
		
		List<QueryReference> references = new ArrayList<QueryReference>();
		references.add(r1);
		references.add(r2);
		
		QueryMethod m1 = new QueryMethod();
		m1.setName("setPreviousResult");
		m1.setClazzFqn("org.apache.jmeter.threads.JMeterContext");
		m1.setArguments(1);
		m1.setReturnTypeFqn("void");
		m1.setConstructor(false);
		m1.setHash(1383734641);
		
		QueryMethod m2 = new QueryMethod();
		m2.setName("URLRewritingModifier");
		m2.setArguments(0);
		m2.setReturnTypeFqn("void");
		m2.setConstructor(true);
		m2.setHash(0);
		
		List<QueryMethod> invocations = new ArrayList<QueryMethod>();
		invocations.add(m1);
		invocations.add(m2);
		
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
		Map<Integer, VotingResult> results = manager.match(q);
		
		print(results);
	}

	private void print(Map<Integer, VotingResult> results) {
		for (Integer id : results.keySet()) {
			System.out.println(id + " " + results.get(id));
		}
	}

}
