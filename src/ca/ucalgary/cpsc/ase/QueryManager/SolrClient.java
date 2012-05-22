package ca.ucalgary.cpsc.ase.QueryManager;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrClient {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 * @throws SolrServerException 
	 */
	public static void main(String[] args) throws MalformedURLException, SolrServerException {
		String url = "http://localhost:8983/solr";
		SolrServer server = new CommonsHttpSolrServer(url);
		
		SolrQuery query = new SolrQuery();
	    query.setQuery("*:*");
	    query.setRows(25);
	    QueryResponse queryResponse = server.query(query);
	    
	    SolrDocumentList docs = queryResponse.getResults();
	    long count = docs.getNumFound();
	    System.out.println("#hits=" + count);
	    
	    for (int i = 0; i < docs.size(); ++i) {
	    	SolrDocument doc = docs.get(i);
	    	
	    	String id = doc.getFieldValue("id").toString();
	    	String tmn = (String) doc.getFieldValue("test_method_name");
	      
	    	System.out.println(id + " " + tmn);
	    }
	}

}
