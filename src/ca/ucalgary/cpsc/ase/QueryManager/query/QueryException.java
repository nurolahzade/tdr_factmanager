package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryException implements QueryElement {

	private String clazzFqn;

	public String getClazzFqn() {
		return clazzFqn;
	}

	public void setClazzFqn(String clazzFqn) {
		this.clazzFqn = clazzFqn;
	}		
	
}
