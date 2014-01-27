package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;

public class SolrClassSignatureQueryProcessor extends
		AbstractSolrHeuristicHelper {

	protected Query query;
	protected String fqn;
	protected StringBuilder q;

	public SolrClassSignatureQueryProcessor(Query query, String fqn) 
		throws MalformedURLException {
		server = new CommonsHttpSolrServer("http://localhost:8983/solr/classes");
		this.query = query;
		this.fqn = fqn;
		q = new StringBuilder();
	}

	@Override
	protected String generateQuery() {
		q.append("class_fqn:");
		q.append(escapeFQN(fqn));
		
		List<QueryMethod> methods = filterMethods();
		if (methods.size() > 0) {
			String names = generateMethodNames(methods);
			if (!names.isEmpty()) {
				q.append(" ");
				q.append("method_names:(");
				q.append(names);
				q.append(")");
			}

			String parameters = generateMethodParameters(methods);
			if (!parameters.isEmpty()) {
				q.append(" ");
				q.append("method_argument_fqns:(");
				q.append(parameters);
				q.append(")");
			}
			
			String returns = generateReturnTypes(methods);
			if (!returns.isEmpty()) {
				q.append(" ");
				q.append("method_return_type_fqns:(");
				q.append(returns);
				q.append(")");			
			}
		}				
		
		return q.toString();
	}

	protected String generateReturnTypes(List<QueryMethod> methods) {
		StringBuilder q = new StringBuilder();
		boolean first = true;
		for (QueryMethod method : methods) {
			String fqn = escapeFQN(method.getReturnTypeFqn());
			if (fqn != null && !fqn.isEmpty()) {
				if (!first) {
					q.append(" ");
				}
				else {
					first = false;
				}
				q.append(fqn);					
			}
		}
		return q.toString();
	}

	protected String generateMethodParameters(List<QueryMethod> methods) {
		StringBuilder q = new StringBuilder();
		boolean first = true;
		for (QueryMethod method : methods) {
			String arguments = generateParameters(method);
			if (arguments != null && !arguments.isEmpty()) {
				if (!first) {
					q.append(" ");
				}
				else {
					first = false;
				}
				q.append(arguments);					
			}				
		}
		return q.toString();
	}

	protected String generateMethodNames(List<QueryMethod> methods) {
		StringBuilder q = new StringBuilder();
		boolean first = true;
		for (QueryMethod method : methods) {
			if (!first) {
				q.append(" ");
			}
			else {
				first = false;
			}
			q.append(method.getName());
		}
		return q.toString();
	}

	private List<QueryMethod> filterMethods() {
		List<QueryMethod> filter = new ArrayList<QueryMethod>();
		for (QueryMethod method : query.getInvocations()) {
			if (fqn.equals(method.getClazzFqn())) {
				filter.add(method);
			}
		}
		return filter;
	}

}
