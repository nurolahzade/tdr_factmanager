package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the MethodInvocation database table.
 * 
 */
@Entity
@Table(name="MethodInvocation")
public class MethodInvocation implements Serializable, CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to ControlFlow
	@ManyToOne
	private ControlFlow controlFlow;

	//bi-directional many-to-one association to DataFlow
	@OneToMany(mappedBy="fromMethod")
	private Set<DataFlow> flowsFrom;

	//bi-directional many-to-one association to DataFlow
	@OneToMany(mappedBy="toMethod")
	private Set<DataFlow> flowsTo;

	//bi-directional many-to-one association to TestMethod
    @ManyToOne
	@JoinColumn(name="test_method_id")
	private TestMethod testMethod;

	//bi-directional many-to-one association to Method
    @ManyToOne
	private Method method;

	//bi-directional many-to-one association to Position
    @ManyToOne
	private Position position;

	//bi-directional many-to-one association to Assertion
    @ManyToOne
	private Assertion assertion;

    public MethodInvocation() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ControlFlow getControlFlow() {
		return this.controlFlow;
	}

	public void setControlFlow(ControlFlow controlFlow) {
		this.controlFlow = controlFlow;
	}
	
	public Set<DataFlow> getDataFlowsFrom() {
		return this.flowsFrom;
	}

	public void setDataFlowsFrom(Set<DataFlow> dataFlows) {
		this.flowsFrom = dataFlows;
	}
	
	public Set<DataFlow> getDataFlowsTo() {
		return this.flowsTo;
	}

	public void setDataflowssTo(Set<DataFlow> dataFlows) {
		this.flowsTo = dataFlows;
	}
	
	public TestMethod getTestmethod() {
		return this.testMethod;
	}

	public void setTestmethod(TestMethod testMethod) {
		this.testMethod = testMethod;
	}
	
	public Method getMethod() {
		return this.method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Assertion getAssertion() {
		return this.assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}
	
}