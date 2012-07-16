package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the Package database table.
 * 
 */
@Entity
@Table(name="Package")

@NamedQuery(name="findPakageByFQN", query="SELECT p FROM Pakage p WHERE p.name = :fqn")

public class Pakage implements CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String name;

	//bi-directional many-to-one association to Class
	@OneToMany(mappedBy="pakage")
	private Set<Clazz> clazzs;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Clazz> getClazzs() {
		return clazzs;
	}

	public void setClazzs(Set<Clazz> clazzs) {
		this.clazzs = clazzs;
	}		

}