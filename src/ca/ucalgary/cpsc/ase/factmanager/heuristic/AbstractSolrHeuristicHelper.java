package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import ca.ucalgary.cpsc.ase.common.heuristic.Heuristic;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;

public abstract class AbstractSolrHeuristicHelper implements SolrHeuristicHelper {
		
	public static final String SERVER_URL = "http://localhost:8983/solr";
	
	public static final String TEST_METHOD_NAME = "test_method_name";
	public static final String TEST_CLASS_NAME = "test_class_name";
	public static final String REFERENCE_NAME = "reference_name";
	public static final String REFERENCE_FQN = "reference_fqn";
	public static final String REFERENCE_OWNER_FQN = "reference_owner_fqn";
	public static final String METHOD_NAME = "method_name";
	public static final String METHOD_FQN = "method_fqn";
	public static final String METHOD_RETURN_TYPE_FQN = "method_return_type_fqn";
	public static final String NAMES = "names";
	public static final String FQNS = "fqns";
	
	public static final int MAX_RESULTS = 1000;
	
	protected SolrServer server;
	
	public AbstractSolrHeuristicHelper() throws MalformedURLException {
		server = new CommonsHttpSolrServer(SERVER_URL);
	}

	@Override
	public Map<Integer, ResultItem> fetch() throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setRows(MAX_RESULTS);
		query.setIncludeScore(true);
	    query.setQuery(generateQuery());
	    
	    QueryResponse queryResponse = server.query(query);	    
	    SolrDocumentList docs = queryResponse.getResults();
	    float maxScore = docs.getMaxScore();
		
	    Map<Integer, ResultItem> results = parse(docs, maxScore);
	    
		return results;
	}

	protected abstract String generateQuery();
	
	protected Map<Integer, ResultItem> parse(SolrDocumentList docs, float maxScore) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		Float threshold = getThreshold();
		Iterator<SolrDocument> iter = docs.iterator();
		while (iter.hasNext()) {
	    	SolrDocument doc = iter.next();
	    	Integer id = (Integer) doc.getFieldValue("id");

	    	float score = (Float) doc.getFieldValue("score");
	    	if (score >= threshold) {
		    	double normalizedScore = score / maxScore;
		    	ResultItem item = new ResultItem();
		    	item.setScore(normalizedScore);
		    	results.put(id, item);	    		    		    		
	    	}
	    }
		return results;
	}
	
	protected Float getThreshold() {
		return (float) 0;
	}
	
	protected String escape(String value) {
		return value.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
	}
	
	protected String escapeFQN(String fqn) {
		if (!Heuristic.UNKNOWN.equals(fqn)) {
			if (fqn.startsWith(Heuristic.UNKNOWN_PACKAGE))
				return escape(fqn.replaceFirst(Heuristic.UNKNOWN_PACKAGE, ""));
			else
				return escape(fqn);
		}
		else
			return null;
	}

	protected String generateParameters(QueryMethod method) {
		boolean first = true;
		StringBuilder p = new StringBuilder();
		if (method.getArguments() != null && method.getArguments().size() > 0) {
			for (String parameter : method.getArguments()) {
				String fqn = escapeFQN(parameter);
				if (fqn != null && !fqn.isEmpty()) {
					if (!first) {
						p.append(" OR ");
					}
					else {
						first = false;
					}
					p.append(fqn);
				}
			}
		}
		return p.toString();
	}

}
