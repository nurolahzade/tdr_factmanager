package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.net.MalformedURLException;
import java.util.List;

import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.SolrHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class SolrFieldBasedHeuristic extends SolrHeuristic {
	
	protected StringBuilder query;
	protected boolean first;

	public SolrFieldBasedHeuristic() throws MalformedURLException {
		super();
		
		query = new StringBuilder();
		first = true;
	}
	
	@Override
	protected String generateQuery(Query q) {		
		if (q.getTestMethod() != null) {
			query.append(TEST_METHOD_NAME + ":" + q.getTestMethod().getName());
			first = false;
		}
		
		if (!first) {
			query.append(" AND ");
		}
		
		if (q.getTestClass() != null) {
			query.append(TEST_CLASS_NAME + ":" + q.getTestClass().getName());
			first = false;
		}
		
		query.append(generateReferences(q.getReferences()));
		query.append(generateInvocations(q.getInvocations()));
		
		return query.toString();
	}

	private String generateInvocations(List<QueryMethod> invocations) {
		StringBuilder query = new StringBuilder();
		if (invocations.size() > 0) {
			if (!first) {
				query.append(" AND ");				
			}
			else {
				first = false;
			}
			query.append(METHOD_NAME);
			query.append(":");
			query.append("(");
			for (int i = 0; i < invocations.size(); ++i) {
				query.append(invocations.get(i).getName());
				if (i < invocations.size() - 1) {
					query.append(" OR ");					
				}
			}
			query.append(")");
			query.append(" AND ");
			query.append(METHOD_FQN);
			query.append(":");
			query.append("(");
			for (int i = 0; i < invocations.size(); ++i) {
				query.append(invocations.get(i).getClazzFqn());
				if (i < invocations.size() - 1) {
					query.append(" OR ");					
				}
			}
			query.append(")");
		}
		return query.toString();
	}

	private String generateReferences(List<QueryReference> references) {
		StringBuilder query = new StringBuilder();
		if (references.size() > 0) {
			if (!first) {
				query.append(" AND ");				
			}
			else {
				first = false;
			}
			query.append(REFERENCE_NAME);
			query.append(":");
			query.append("(");
			for (int i = 0; i < references.size(); ++i) {
				query.append(references.get(i).getName());
				if (i < references.size() - 1) {
					query.append(" OR ");					
				}
			}
			query.append(")");
			query.append(" AND ");				
			query.append(REFERENCE_FQN);
			query.append(":");
			query.append("(");
			for (int i = 0; i < references.size(); ++i) {
				query.append(escape(references.get(i).getClazzFqn()));
				if (i < references.size() - 1) {
					query.append(" OR ");					
				}
			}
			query.append(")");
		}
		return query.toString();
	}

	@Override
	public String getName() {
		return "S";
	}
	
}
