package com.nemat.modal;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String userName;
	private String password;
	@ElementCollection
	@CollectionTable(name="user_roles", 
			joinColumns = @JoinColumn(name = "roles_id"))
	private Set<String> roles;

}
