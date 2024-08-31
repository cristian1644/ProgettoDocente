package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Utente;

public interface SquadraRepository extends CrudRepository<Squadra, Long>{
	
	public Optional<Squadra> findById(Long id);
	public Squadra findByNome(String nome);
	boolean existsByIdAndPresidente(Long squadraId, Utente presidente);
}
