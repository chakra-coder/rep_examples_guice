package pl.kwi.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pl.kwi.db.guice.AbstractEntity;

@NamedQuery(name="UserEntity.getAll", 
			query="SELECT u FROM UserEntity u")
@Entity
@Table(name="users")
public class UserEntity extends AbstractEntity{
	
	
	private static final long serialVersionUID = 1L;	
	private String name;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
