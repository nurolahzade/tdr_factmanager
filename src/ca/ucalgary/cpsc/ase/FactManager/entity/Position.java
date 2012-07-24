package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;


/**
 * The persistent class for the position database table.
 * 
 */
@Entity
@Table(name="position")
public class Position implements CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private int length;

	private int start;

    public Position() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}