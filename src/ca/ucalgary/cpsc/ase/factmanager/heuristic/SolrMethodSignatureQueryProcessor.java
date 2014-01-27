package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import ca.ucalgary.cpsc.ase.common.query.QueryMethod;

public class SolrMethodSignatureQueryProcessor extends AbstractSolrHeuristicHelper {

	protected QueryMethod method;
	protected StringBuilder q;
	
	public SolrMethodSignatureQueryProcessor(QueryMethod method) throws MalformedURLException {
		server = new CommonsHttpSolrServer("http://localhost:8983/solr/methods");
		this.method = method;
		q = new StringBuilder();
	}

	@Override
	protected String generateQuery() {
		q.append("name:");
		q.append(method.getName());
		String fqn = escapeFQN(method.getClazzFqn());
		if (fqn != null) {
			q.append(" OR ");
			q.append("fqn:");
			q.append(fqn);
		}
		String returnTypeFqn = escapeFQN(method.getReturnTypeFqn());
		if (returnTypeFqn != null) {
			q.append(" OR ");
			q.append("return_type_fqn:");
			q.append(returnTypeFqn);			
		}
		String parameters = generateParameters(method);
		if (!parameters.isEmpty()) {
			q.append(" OR ");
			q.append(" parameters:(");
			q.append(parameters);
			q.append(")");
		}
		return q.toString();
	}

	@Override
	protected Float getThreshold() {
		return (float) 0.5;
	}	

}
