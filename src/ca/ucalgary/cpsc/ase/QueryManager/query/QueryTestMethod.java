package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryTestMethod extends QueryElement {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("()");
		return builder.toString();
	}

	@Override
	public String getCaption() {
		return "Test Method";
	}

}
