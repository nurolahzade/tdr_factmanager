package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the Assertion database table.
 * 
 */
@Entity
@Table(name="Assertion")
@NamedQueries({
	@NamedQuery(name="findByType", query="SELECT a FROM Assertion a " +
			"WHERE a.type = :type"),
	
	@NamedQuery(name="FindMatchingAssertions", query="SELECT DISTINCT a.assertion " +
			"FROM TestMethod tm, IN(tm.assertions) a " +
			"WHERE tm.clazz.id = :id AND a.assertion.id IN :list")
})

public class Assertion implements CodeEntity, Invocation {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private AssertionType type;

	//bi-directional one-to-many association to TestMethodHasAssertion
	@OneToMany(mappedBy="assertion")
	private Set<TestMethodHasAssertion> testMethods;
	
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
	
	public Set<TestMethodHasAssertion> getTestMethods() {
		return this.testMethods;
	}

	public void setTestMethods(Set<TestMethodHasAssertion> testMethods) {
		this.testMethods = testMethods;
	}

	@Override
	public String toString() {
		return type.getName();
	}	
	
}