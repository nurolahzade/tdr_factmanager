package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import ca.ucalgary.cpsc.ase.common.heuristic.Heuristic;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryException;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;
import ca.ucalgary.cpsc.ase.common.query.QueryReference;

public class SolrNamesAndFQNsQueryProcessor extends AbstractSolrHeuristicHelper {
		
	protected Query query;
	protected StringBuilder q;
	protected Set<String> names;
	protected Set<String> fqns;	
	
	public SolrNamesAndFQNsQueryProcessor(Query query) throws MalformedURLException {
		super();
		this.query = query;
		q = new StringBuilder();
		names = new HashSet<String>();
		fqns = new HashSet<String>();
	}

	@Override
	protected String generateQuery() {
		extractNamesAndFQNs();
		
		generateNames();
		generateFQNs();
		
		return q.toString();
	}

	protected void generateNames() {
		if (names.size() > 0) {
			q.append(NAMES);
			q.append(":");
			q.append("(");
			boolean first = true;
			for (String name : names) {
				if (!first) {
					q.append(" OR ");
				}
				else {
					first = false;
				}
				q.append(name);
			}
			q.append(")");
		}
	}

	protected void generateFQNs() {
		if (names.size() > 0) {
			if (names.size() > 0) {
				q.append(" AND ");
			}
			q.append(FQNS);
			q.append(":");
			q.append("(");
			boolean first = true;
			for (String fqn : fqns) {
				String escapedFqn = escapeFQN(fqn);
				if (escapedFqn != null) {
					if (! first) {
						q.append(" OR ");
					}
					else {
						first = false;
					}					
					q.append(escapedFqn);
				}
			}
			q.append(")");
		}		
	}

	protected void extractNamesAndFQNs() {
		if (query.getTestMethod() != null) {
			names.add(query.getTestMethod().getName());			
		}
		
		if (query.getTestMethod() != null) {
			names.add(query.getTestClass().getName());
		}
		
		if (query.getReferences() != null && query.getReferences().size() > 0) {
			for (QueryReference reference : query.getReferences()) {
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
		
		if (query.getInvocations() != null && query.getInvocations().size() > 0) {
			for (QueryMethod method : query.getInvocations()) {
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
		
		if (query.getExceptions() != null && query.getExceptions().size() > 0) {
			for (QueryException exception : query.getExceptions()) {
				if (exception.getClazzFqn() != null) {
					fqns.add(exception.getClazzFqn());					
				}
			}
		}
	}
	
}
