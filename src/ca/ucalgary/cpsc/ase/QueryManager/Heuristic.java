package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.SortedMap;

public interface Heuristic {
	
	public SortedMap<Integer, ResultItem> match(Query q);

}
