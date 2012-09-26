package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Map;


public interface Heuristic {

	public static final String UNKNOWN = "UNKNOWNP.UNKNOWN";

	public Map<Integer, ResultItem> match(Query q) throws Exception;
	public String getName();
}
