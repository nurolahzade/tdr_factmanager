package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryTestClass implements QueryElement {

	private String name;
	private String packageName;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getQualifiedName() {
		return (packageName != null ? packageName + "." : "") + name; 
	}
		
}
