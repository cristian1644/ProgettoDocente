package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{

	public Credentials findByUsername(String username);
	public Credentials findByEmail(String email);
	public List<Credentials> findByRole(String role);
}
