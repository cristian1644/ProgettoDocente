package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.TesseramentoGiocatore;

public interface TesseramentoGiocatoreRepository extends CrudRepository<TesseramentoGiocatore, Long>{

	public List<TesseramentoGiocatore> findBySquadra(Squadra squadra);
	public List<TesseramentoGiocatore> findByGiocatore(Giocatore giocatore);
	public List<TesseramentoGiocatore> findByGiocatoreId(Long giocatoreId);
	
}
