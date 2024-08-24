package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.repository.TesseramentoGiocatoreRepository;

@Service
public class TesseramentoGiocatoreService {

	@Autowired
	private TesseramentoGiocatoreRepository tesseramentoGiocatoreRepository;
	
	public TesseramentoGiocatore findById(Long id) {
		return tesseramentoGiocatoreRepository.findById(id).get();
	}

	public Iterable<TesseramentoGiocatore> findAll() {
		return tesseramentoGiocatoreRepository.findAll();
	}
}
