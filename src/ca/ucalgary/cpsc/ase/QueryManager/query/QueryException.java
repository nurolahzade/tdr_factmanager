package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryException implements QueryElement {

	private String clazzFqn;

	public String getClazzFqn() {
		return clazzFqn;
	}

	public void setClazzFqn(String clazzFqn) {
		this.clazzFqn = clazzFqn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryException [clazzFqn=");
		builder.append(clazzFqn);
		builder.append("]");
		return builder.toString();
	}		
	
}
