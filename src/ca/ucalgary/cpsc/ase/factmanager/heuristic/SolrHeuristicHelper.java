package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;

public interface SolrHeuristicHelper {

	public abstract Map<Integer, ResultItem> fetch() throws SolrServerException;

}