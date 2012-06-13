package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.SolrHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryException;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class SolrNamesAndFQNsHeuristic extends SolrHeuristic {

	protected StringBuilder query;
	protected Set<String> names;
	protected Set<String> fqns;	
	
	public SolrNamesAndFQNsHeuristic() throws MalformedURLException {
		super();
		
		query = new StringBuilder();
		names = new HashSet<String>();
		fqns = new HashSet<String>();
	}

	@Override
	protected String generateQuery(Query q) {
		extractNamesAndFQNs(q);
		
		generateNames();
		generateFQNs();
		
		return query.toString();
	}

	protected void generateNames() {
		if (names.size() > 0) {
			query.append(NAMES);
			query.append(":");
			query.append("(");
			boolean first = true;
			for (String name : names) {
				if (!first) {
					query.append(" OR ");
				}
				else {
					first = false;
				}
				query.append(name);
			}
			query.append(")");
		}
	}

	protected void generateFQNs() {
		if (names.size() > 0) {
			if (names.size() > 0) {
				query.append(" AND ");
			}
			query.append(FQNS);
			query.append(":");
			query.append("(");
			boolean first = true;
			for (String fqn : fqns) {
				if (!first) {
					query.append(" OR ");
				}
				else {
					first = false;
				}
				query.append(fqn);
			}
			query.append(")");
		}		
	}

	protected void extractNamesAndFQNs(Query q) {
		if (q.getMethodName() != null) {
			names.add(q.getMethodName());			
		}
		
		if (q.getClassName() != null) {
			names.add(q.getClassName());
		}
		
		if (q.getReferences() != null && q.getReferences().size() > 0) {
			for (QueryReference reference : q.getReferences()) {
				if (reference.getName() != null) {
					names.add(reference.getName());					
				}
				if (reference.getClazzFqn() != null) {
					fqns.add(reference.getClazzFqn());					
				}
				if (reference.getDeclaringClazzFqn() != null) {
					fqns.add(reference.getDeclaringClazzFqn());					
				}
			}
		}
		
		if (q.getInvocations() != null && q.getInvocations().size() > 0) {
			for (QueryMethod method : q.getInvocations()) {
				if (method.getName() != null) {
					names.add(method.getName());					
				}
				if (method.getClazzFqn() != null) {
					fqns.add(method.getClazzFqn());					
				}
				if (method.getReturnTypeFqn() != null) {
					fqns.add(method.getReturnTypeFqn());					
				}
			}
		}
		
		if (q.getExceptions() != null && q.getExceptions().size() > 0) {
			for (QueryException exception : q.getExceptions()) {
				if (exception.getClazzFqn() != null) {
					fqns.add(exception.getClazzFqn());					
				}
			}
		}
	}

}
