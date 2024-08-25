package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Utente;

public interface PresidenteRepository extends CrudRepository<Utente, Long>{

	public Optional<Utente> findById(Long id);
}
