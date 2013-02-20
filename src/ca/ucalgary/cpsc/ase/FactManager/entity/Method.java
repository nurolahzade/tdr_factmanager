package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;

import java.util.Set;
import java.util.List;


/**
 * The persistent class for the Method database table.
 * 
 */
@Entity
@Table(name="Method")
@NamedQueries({
		@NamedQuery(name="FindMethod", query="SELECT m FROM Method m WHERE m.name = :name AND m.clazz = :clazz " +
				"AND m.returnClazz = :returnClazz AND SIZE(m.arguments) = :arguments AND m.hash = :hash"),
				
		@NamedQuery(name="FindMethodByFQN", query="SELECT m FROM Method m " +
				"WHERE m.name = :name AND m.clazz.fqn = :fqn AND SIZE(m.arguments) = :arguments"),
		
		@NamedQuery(name="FindMatchingCalls", query="SELECT DISTINCT i.method " +
				"FROM TestMethod tm, IN(tm.invocations) i " +
				"WHERE tm.clazz.id = :id AND i.method.id IN :list")//,
				
//		@NamedQuery(name="FindMatchingAssertionParameters", query="SELECT DISTINCT aom.method " +
//				"FROM AssertionOnMethod aom " +
//				"WHERE aom.testMethod.clazz.id = :id AND aom.method.id IN :list")
})

public class Method implements CodeEntity, Invocation {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Class
    @ManyToOne
	@JoinColumn(name="class_id")
	private Clazz clazz;

	//uni-directional many-to-one association to Class
    @ManyToOne
	@JoinColumn(name="return_type")
	private Clazz returnClazz;
    
    @Column(name="constructor")
    private Boolean constructor;
    
    @Column(name="hash")
    private Integer hash;

	//bi-directional many-to-one association to MethodInvocation
	@OneToMany(mappedBy="method")
	private Set<MethodInvocation> invocations;
    
//	//bi-directional many-to-many association to TestMethodCallsMethod
//	@OneToMany(mappedBy="method")
//	private Set<TestMethodCallsMethod> testMethods;
	
	//bi-directional many-to-many association to Argument
	@OneToMany(mappedBy="method")
	private List<Argument> arguments;

	public Method() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Clazz getClazz() {
		return this.clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	
	public Clazz getReturnClazz() {
		return this.returnClazz;
	}

	public void setReturnClazz(Clazz returnClazz) {
		this.returnClazz = returnClazz;
	}		
	
	public Boolean isConstructor() {
		return constructor;
	}

	public void setConstructor(Boolean constructor) {
		this.constructor = constructor;
	}

//	public Set<TestMethodCallsMethod> getTestMethods() {
//		return this.testMethods;
//	}

	public Integer getHash() {
		return hash;
	}

	public void setHash(Integer hash) {
		this.hash = hash;
	}

	public Set<MethodInvocation> getInvocations() {
		return this.invocations;
	}

	public void setInvocations(Set<MethodInvocation> invocations) {
		this.invocations = invocations;
	}	
	
//	public void setTestMethods(Set<TestMethodCallsMethod> testMethods) {
//		this.testMethods = testMethods;
//	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> arguments) {
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		return clazz.getFqn() + "." + name + "(" + arguments.size() + "):" + 
			returnClazz.getFqn();
	}	
	
}