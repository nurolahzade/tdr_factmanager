package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryReference implements QueryElement {
	
	private String clazzFqn;
	private String declaringClazzFqn;
	private String name;
	
	public String getClazzFqn() {
		return clazzFqn;
	}
	public void setClazzFqn(String fqn) {
		this.clazzFqn = fqn;
	}
	public String getDeclaringClazzFqn() {
		return declaringClazzFqn;
	}
	public void setDeclaringClazzFqn(String fqn) {
		this.declaringClazzFqn = fqn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
