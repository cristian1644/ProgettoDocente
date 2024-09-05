package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;

@Service
public class GiocatoreService {

	@Autowired
	private GiocatoreRepository giocatoreRepository;
	
	public Giocatore findById(Long id) {
		return giocatoreRepository.findById(id).get();
	}

	public Iterable<Giocatore> findAll() {
		return giocatoreRepository.findAll();
	}
	
	public boolean giocatoreEsiste(String nome, String cognome) {
		return this.giocatoreRepository.findByNomeAndCognome(nome, cognome).isPresent();
	}
}
