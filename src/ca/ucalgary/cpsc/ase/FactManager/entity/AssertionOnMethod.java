package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Assertion_on_Method database table.
 * 
 */
@Entity
@Table(name="Assertion_on_Method")

@NamedQuery(name="Find", query="SELECT aom FROM AssertionOnMethod aom WHERE aom.assertion=:assertion AND aom.method=:method AND aom.testMethod=:testMethod")

public class AssertionOnMethod implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AssertionOnMethodPK id;

	//bi-directional many-to-one association to Assertion
    @ManyToOne
    @JoinColumn(name="assertion_id", insertable=false, updatable=false)
	private Assertion assertion;

	//bi-directional many-to-one association to Method
    @ManyToOne
    @JoinColumn(name="method_id", insertable=false, updatable=false)
	private Method method;

	//bi-directional many-to-one association to TestMethod
    @ManyToOne
	@JoinColumn(name="test_method_id", insertable=false, updatable=false)
	private TestMethod testMethod;

    public AssertionOnMethod() {
    }

	public AssertionOnMethodPK getId() {
		return this.id;
	}

	public void setId(AssertionOnMethodPK id) {
		this.id = id;
	}
	
	public Assertion getAssertion() {
		return this.assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}
	
	public Method getMethod() {
		return this.method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public TestMethod getTestMethod() {
		return this.testMethod;
	}

	public void setTestMethod(TestMethod testMethod) {
		this.testMethod = testMethod;
	}
	
}