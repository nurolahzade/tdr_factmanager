package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryAssertionParameter extends QueryElement {

	private QueryMethod method;
	private QueryAssertion assertion;

	public QueryMethod getMethod() {
		return method;
	}
	
	public void setMethod(QueryMethod method) {
		this.method = method;
	}
	
	public QueryAssertion getAssertion() {
		return assertion;
	}
	
	public void setAssertion(QueryAssertion assertion) {
		this.assertion = assertion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(assertion);
		builder.append("(");
		builder.append(method);
		builder.append(")");
		return builder.toString();
	}

	@Override
	public String getCaption() {
		return "Assertion Parameters";
	}
		 	
}
