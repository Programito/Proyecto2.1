package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Collection {

	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private String id;
	 
	 private String name; 
	 private String description;
	 
	


	// No persistir este miembro
	 @Transient
	 private String size; 

	 @OneToMany(mappedBy = "collection", 
			 	cascade = CascadeType.ALL, orphanRemoval=true)
	 private Set<Item> items = new HashSet<>(); 
	 
	 
	
	 @ManyToOne(cascade={CascadeType.PERSIST})
	 @JoinColumn(name="USER_ID")
	 private User user;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void Description(String description) {
		this.description = description;
	}


	public String getId() {
		return id;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public Set<Item> getItems() {
		return items;
	}


	public void setItems(Set<Item> items) {
		this.items = items;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user=user; 
	}


	public void setId(String id) {
		this.id = id; 
	}

	 public void setDescription(String description) {
			this.description = description;
	}
	
	
	 
}
