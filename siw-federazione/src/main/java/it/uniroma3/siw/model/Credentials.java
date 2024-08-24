package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credentials {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String email;
	private String role;
	
	@OneToOne
	private Utente user;
	
	//setter e getter
	
	public String getUsername() {
		return username;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Utente getUser() {
		return user;
	}
	public void setUser(Utente user) {
		this.user = user;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//equals e hashcode
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}	
	
}
