package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.repository.TesseramentoGiocatoreRepository;

@Service
public class TesseramentoGiocatoreService {

	@Autowired
	private TesseramentoGiocatoreRepository tesseramentoGiocatoreRepository;
	
	public Optional<TesseramentoGiocatore> findById(Long id) {
		return tesseramentoGiocatoreRepository.findById(id);
	}

	public Iterable<TesseramentoGiocatore> findAll() {
		return tesseramentoGiocatoreRepository.findAll();
	}

	public void save(TesseramentoGiocatore tesseramentoGiocatore) {
		// TODO Auto-generated method stub
		this.tesseramentoGiocatoreRepository.save(tesseramentoGiocatore);
	}

	public List<TesseramentoGiocatore> findBySquadra(Squadra squadra) {
		// TODO Auto-generated method stub
		return this.tesseramentoGiocatoreRepository.findBySquadra(squadra);
	}
}
