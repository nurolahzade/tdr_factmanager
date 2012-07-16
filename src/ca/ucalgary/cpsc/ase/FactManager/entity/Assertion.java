package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the Assertion database table.
 * 
 */
@Entity
@NamedQuery(name="findByType", query="SELECT a FROM Assertion a WHERE a.type = :type")

public class Assertion implements CodeEntity, Invocation {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private AssertionType type;

	//bi-directional many-to-many association to TestMethod
	@ManyToMany
	@JoinTable(
		name="TestMethod_has_Assertion"
		, joinColumns={
			@JoinColumn(name="assertion_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="test_method_id")
			}
		)
	private Set<TestMethod> testMethods;
	
    public Assertion() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}		

	public AssertionType getType() {
		return type;
	}

	public void setType(AssertionType type) {
		this.type = type;
	}
	
	public Set<TestMethod> getTestMethods() {
		return this.testMethods;
	}

	public void setTestMethods(Set<TestMethod> testMethods) {
		this.testMethods = testMethods;
	}
	
}