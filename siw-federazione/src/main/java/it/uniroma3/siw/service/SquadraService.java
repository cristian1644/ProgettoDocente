package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;

@Service
public class SquadraService {

	@Autowired
	private SquadraRepository squadraRepository;
	
	public Squadra findById(Long id) {
		return squadraRepository.findById(id).get();
	}

	public Iterable<Squadra> findAll() {
		return squadraRepository.findAll();
	}
	
	public Squadra findByNome(String nome) {
		return this.squadraRepository.findByNome(nome);
	}

}
