package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.List;

public interface Heuristic {
	
	public List<ResultItem> match(Query q);

}
