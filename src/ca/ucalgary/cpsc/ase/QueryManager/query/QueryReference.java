package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryReference extends QueryElement {
	
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (declaringClazzFqn != null && declaringClazzFqn.length() > 0) {
			builder.append(declaringClazzFqn);
			builder.append(".");
		}
		builder.append(name);
		builder.append(":");
		builder.append(clazzFqn);
		return builder.toString();
	}
	@Override
	public String getCaption() {
		return "References";
	}

}
