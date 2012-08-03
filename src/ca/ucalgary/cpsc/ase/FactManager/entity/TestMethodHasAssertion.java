package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the testmethod_has_assertion database table.
 * 
 */
@Entity
@Table(name="TestMethod_has_Assertion")
@NamedQuery(name="findTestMethodAssertion", query="SELECT tmha FROM TestMethodHasAssertion tmha " +
		"WHERE tmha.testMethod = :testMethod AND tmha.assertion = :assertion")

public class TestMethodHasAssertion implements Serializable, CodeEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TestMethodHasAssertionPK id;

	//bi-directional many-to-one association TestMethod
    @ManyToOne
    @JoinColumn(name="test_method_id", insertable=false, updatable=false)
    private TestMethod testMethod;
    
	//bi-directional many-to-one association Assertion
    @ManyToOne
    @JoinColumn(name="assertion_id", insertable=false, updatable=false)
	private Assertion assertion;
    
	@OneToOne
	private Position position;

    public TestMethodHasAssertion() {
    }    

	public TestMethod getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(TestMethod testMethod) {
		this.testMethod = testMethod;
	}

	public Assertion getAssertion() {
		return assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}

	public TestMethodHasAssertionPK getId() {
		return this.id;
	}

	public void setId(TestMethodHasAssertionPK id) {
		this.id = id;
	}
	
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}