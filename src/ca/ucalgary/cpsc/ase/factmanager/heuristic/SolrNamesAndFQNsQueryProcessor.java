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
			q.append(getNames());
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
	
	protected String getNames() {
		return NAMES;
	}

	protected void generateFQNs() {
//		if (names.size() > 0) {
			if (names.size() > 0) {
				q.append(" OR ");
			}
			q.append(getFQNs());
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
//		}		
	}
	
	protected String getFQNs() {
		return FQNS;
	}

	protected void extractNamesAndFQNs() {
		extractTestMethodName();		
		extractTestClassName();		
		extractReferences();		
		extractInvocations();		
		extractExceptions();
	}

	protected void extractExceptions() {
		if (query.getExceptions() != null && query.getExceptions().size() > 0) {
			for (QueryException exception : query.getExceptions()) {
				if (exception.getClazzFqn() != null) {
					fqns.add(exception.getClazzFqn());					
				}
			}
		}
	}

	protected void extractInvocations() {
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
	}

	protected void extractReferences() {
		if (query.getReferences() != null && query.getReferences().size() > 0) {
			for (QueryReference reference : query.getReferences()) {
				extractReferenceName(reference);
				extractReferenceType(reference);
				extractReferenceOwner(reference);
			}
		}
	}

	protected void extractReferenceOwner(QueryReference reference) {
		if (reference.getDeclaringClazzFqn() != null) {
			fqns.add(reference.getDeclaringClazzFqn());					
		}
	}

	protected void extractReferenceType(QueryReference reference) {
		if (reference.getClazzFqn() != null) {
			fqns.add(reference.getClazzFqn());					
		}
	}

	protected void extractReferenceName(QueryReference reference) {
		if (reference.getName() != null) {
			names.add(reference.getName());					
		}
	}

	protected void extractTestClassName() {
		if (query.getTestMethod() != null) {
			names.add(query.getTestClass().getName());
		}
	}

	protected void extractTestMethodName() {
		if (query.getTestMethod() != null) {
			names.add(query.getTestMethod().getName());			
		}
	}
	
}
