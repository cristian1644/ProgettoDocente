package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long>{

	Optional<Giocatore> findByNomeAndCognome(String nome, String cognome);
}
