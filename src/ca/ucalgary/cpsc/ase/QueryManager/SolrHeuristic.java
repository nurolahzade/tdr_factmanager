package ca.ucalgary.cpsc.ase.QueryManager;

import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public abstract class SolrHeuristic implements Heuristic {
		
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
	
	protected SolrServer server;
	
	public SolrHeuristic() throws MalformedURLException {
		server = new CommonsHttpSolrServer(SERVER_URL);
	}

	@Override
	public Map<Integer, ResultItem> match(Query q) throws SolrServerException {
		SolrQuery query = new SolrQuery();
	    query.setQuery(generateQuery(q));
	    
	    QueryResponse queryResponse = server.query(query);	    
	    SolrDocumentList docs = queryResponse.getResults();
		
	    Map<Integer, ResultItem> results = parse(docs);
	    
		return results;
	}

	protected abstract String generateQuery(Query q);
	
	protected Map<Integer, ResultItem> parse(SolrDocumentList docs) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		System.out.print(this.getClass().getName() + ": ");
		for (int i = 0; i < docs.size(); ++i) {
	    	SolrDocument doc = docs.get(i);
	    	
	    	Integer id = (Integer) doc.getFieldValue("id");
	    	// TODO refactor or replace ResultItem with something extracted from Solr index
	    	results.put(id, null);	    		    	
			System.out.print(id + ", ");
	    }
		System.out.println();
		return results;
	}
	
	protected String escape(String value) {
		return value.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
	}

}
