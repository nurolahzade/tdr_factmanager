package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Map;


public interface Heuristic {

	public abstract Map<Integer, ResultItem> match(Query q) throws Exception;
	
}
