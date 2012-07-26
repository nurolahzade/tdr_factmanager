package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;


/**
 * The persistent class for the RepositoryFile database table.
 * 
 */
@Entity
@Table(name="RepositoryFile")
@NamedQuery(name="FindUnvisited", query="SELECT rf FROM RepositoryFile rf WHERE rf.visited=false")

public class RepositoryFile implements CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String path;

	private Boolean visited;

    public RepositoryFile() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean isVisited() {
		return this.visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

}