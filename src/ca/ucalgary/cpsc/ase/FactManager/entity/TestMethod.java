package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TestMethod database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="MatchReference", query="SELECT tm, COUNT(DISTINCT r.clazz) " +
			"FROM TestMethod tm, IN(tm.references) r " +
			"WHERE r.clazz.fqn IN :fqns " +
			"GROUP BY tm " +
			"ORDER BY COUNT(DISTINCT r.clazz) DESC"),
	@NamedQuery(name="MatchSimpleCall", query="SELECT tm, COUNT(m) " +
			"FROM TestMethod tm, IN(tm.invocations) m " +
			"WHERE m.id IN :list " +
			"GROUP BY tm " +
			"ORDER BY COUNT(m) DESC"),
	@NamedQuery(name="MatchAssertion", query="SELECT tm, COUNT(DISTINCT a) " +
			"FROM TestMethod tm, IN(tm.assertions) a " +
			"WHERE a.id IN :list " +
			"GROUP BY tm " +
			"ORDER BY COUNT(DISTINCT a) DESC")
})

public class TestMethod implements CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="last_modified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	//bi-directional many-to-one association to Reference
	@OneToMany(mappedBy="testMethod")
	private Set<Reference> references;

	//bi-directional many-to-many association to Assertion
    @ManyToMany(mappedBy="testMethods")
	private Set<Assertion> assertions;

	//bi-directional many-to-one association to Class
    @ManyToOne
	@JoinColumn(name="class_id")
	private Clazz clazz;

	//bi-directional many-to-many association to Exception
    @ManyToMany(mappedBy="testMethods")
	private Set<Xception> xceptions;

	//bi-directional many-to-many association to Method
    @ManyToMany(mappedBy="testMethods")    
    private Set<Method> invocations;

    public TestMethod() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Set<Reference> getReferences() {
		return this.references;
	}

	public void setReferences(Set<Reference> references) {
		this.references = references;
	}
	
	public Set<Assertion> getAssertions() {
		return this.assertions;
	}

	public void setAssertions(Set<Assertion> assertions) {
		this.assertions = assertions;
	}
	
	public Clazz getClazz() {
		return this.clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	
	public Set<Xception> getXceptions() {
		return this.xceptions;
	}

	public void setXceptions(Set<Xception> xceptions) {
		this.xceptions = xceptions;
	}
	
	public Set<Method> getInvocations() {
		return this.invocations;
	}

	public void setInvocations(Set<Method> invocations) {
		this.invocations = invocations;
	}
	
}