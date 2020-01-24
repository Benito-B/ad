package model;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombre")
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	private String[] editableFields = {"name"};

	@Override
	public String toString() {
		return name;
	}
}
