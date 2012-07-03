package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryMethod implements QueryInvocation {

	private String name;
	private String clazzFqn;
	private String returnTypeFqn;
	private int arguments;
	private int hash;
	private boolean constructor;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClazzFqn() {
		return clazzFqn;
	}
	public void setClazzFqn(String clazzFqn) {
		this.clazzFqn = clazzFqn;
	}
	public String getReturnTypeFqn() {
		return returnTypeFqn;
	}
	public void setReturnTypeFqn(String returnTypeFqn) {
		this.returnTypeFqn = returnTypeFqn;
	}
	public int getArguments() {
		return arguments;
	}
	public void setArguments(int arguments) {
		this.arguments = arguments;
	}
	public int getHash() {
		return hash;
	}
	public void setHash(int hash) {
		this.hash = hash;
	}
	public boolean isConstructor() {
		return constructor;
	}
	public void setConstructor(boolean constructor) {
		this.constructor = constructor;
	}
	
	
}
