package ca.ucalgary.cpsc.ase.QueryManager;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;

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
	
	public static final int MAX_RESULTS = 100;
	
	protected SolrServer server;
	
	public SolrHeuristic() throws MalformedURLException {
		server = new CommonsHttpSolrServer(SERVER_URL);
	}

	@Override
	public Map<Integer, ResultItem> match(Query q) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setRows(MAX_RESULTS);
		query.setIncludeScore(true);
	    query.setQuery(generateQuery(q));
	    
	    QueryResponse queryResponse = server.query(query);	    
	    SolrDocumentList docs = queryResponse.getResults();
	    float maxScore = docs.getMaxScore();
		
	    Map<Integer, ResultItem> results = parse(docs, maxScore);
	    
		return results;
	}

	protected abstract String generateQuery(Query q);
	
	protected Map<Integer, ResultItem> parse(SolrDocumentList docs, float maxScore) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		TestMethodService service = new TestMethodService();
		
		System.out.print(this.getClass().getName() + ": ");
		Iterator<SolrDocument> iter = docs.iterator();
		while (iter.hasNext()) {
	    	SolrDocument doc = iter.next();
	    	Integer id = (Integer) doc.getFieldValue("id");
	    	float score = (Float) doc.getFieldValue("score");
	    	double normalizedScore = score / maxScore;
	    	// TODO refactor or replace ResultItem with something extracted from Solr index
	    	ResultItem item = new ResultItem();
	    	TestMethod tm = service.find(id);
	    	item.setTarget(tm);
	    	item.setScore(normalizedScore);
	    	results.put(id, item);	    		    	
			System.out.print(id + ", ");
	    }
		System.out.println();
		return results;
	}
	
	protected String escape(String value) {
		return value.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
	}

}
