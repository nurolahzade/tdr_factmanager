package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.List;
import java.util.Map;

public interface Heuristic {

	public static final String UNKNOWN = "UNKNOWNP.UNKNOWN";

	public Map<Integer, ResultItem> match(Query q) throws Exception;
	public List getMatchingItems(Integer id, Query q);
	public String getName();
	public String getFullName();
}
