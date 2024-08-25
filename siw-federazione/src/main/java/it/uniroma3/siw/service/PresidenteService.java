package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.PresidenteRepository;

@Service
public class PresidenteService {

	@Autowired
	private PresidenteRepository presidenteRepository;
	
	public Utente findById(Long id) {
		return presidenteRepository.findById(id).get();
	}

	public Iterable<Utente> findAll() {
		return presidenteRepository.findAll();
	}
}
