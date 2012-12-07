package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryException extends QueryElement {

	private String clazzFqn;

	public String getClazzFqn() {
		return clazzFqn;
	}

	public void setClazzFqn(String clazzFqn) {
		this.clazzFqn = clazzFqn;
	}

	@Override
	public String toString() {
		return clazzFqn;
	}

	@Override
	public String getCaption() {
		return "Exceptions";
	}		
	
}
