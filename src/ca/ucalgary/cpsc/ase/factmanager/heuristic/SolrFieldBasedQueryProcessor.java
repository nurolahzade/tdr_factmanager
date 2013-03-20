package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.net.MalformedURLException;
import java.util.List;

import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;
import ca.ucalgary.cpsc.ase.common.query.QueryReference;

public class SolrFieldBasedQueryProcessor extends AbstractSolrHeuristicHelper {
	
	protected Query query;
	protected StringBuilder q;
	protected boolean first;

	public SolrFieldBasedQueryProcessor(Query query) throws MalformedURLException {
		super();
		this.query = query;
		q = new StringBuilder();
		first = true;
	}
	
	@Override
	protected String generateQuery() {		
		if (query.getTestMethod() != null) {
			q.append(TEST_METHOD_NAME + ":" + query.getTestMethod().getName());
			first = false;
		}
		
		if (!first) {
			q.append(" AND ");
		}
		
		if (query.getTestClass() != null) {
			q.append(TEST_CLASS_NAME + ":" + query.getTestClass().getName());
			first = false;
		}
		
		q.append(generateReferences(query.getReferences()));
		q.append(generateInvocations(query.getInvocations()));
		
		return q.toString();
	}

	private String generateInvocations(List<QueryMethod> invocations) {
		StringBuilder q = new StringBuilder();
		if (invocations.size() > 0) {
			if (!first) {
				q.append(" AND ");				
			}
			else {
				first = false;
			}
			q.append(METHOD_NAME);
			q.append(":");
			q.append("(");
			for (int i = 0; i < invocations.size(); ++i) {
				q.append(invocations.get(i).getName());
				if (i < invocations.size() - 1) {
					q.append(" OR ");					
				}
			}
			q.append(")");
			q.append(" AND ");
			q.append(METHOD_FQN);
			q.append(":");
			q.append("(");
			for (int i = 0; i < invocations.size(); ++i) {
				q.append(invocations.get(i).getClazzFqn());
				if (i < invocations.size() - 1) {
					q.append(" OR ");					
				}
			}
			q.append(")");
		}
		return q.toString();
	}

	private String generateReferences(List<QueryReference> references) {
		StringBuilder q = new StringBuilder();
		if (references.size() > 0) {
			if (!first) {
				q.append(" AND ");				
			}
			else {
				first = false;
			}
			q.append(REFERENCE_NAME);
			q.append(":");
			q.append("(");
			for (int i = 0; i < references.size(); ++i) {
				q.append(references.get(i).getName());
				if (i < references.size() - 1) {
					q.append(" OR ");					
				}
			}
			q.append(")");
			q.append(" AND ");				
			q.append(REFERENCE_FQN);
			q.append(":");
			q.append("(");
			for (int i = 0; i < references.size(); ++i) {
				q.append(escape(references.get(i).getClazzFqn()));
				if (i < references.size() - 1) {
					q.append(" OR ");					
				}
			}
			q.append(")");
		}
		return q.toString();
	}

}
