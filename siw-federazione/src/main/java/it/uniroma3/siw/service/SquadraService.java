package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import jakarta.persistence.EntityNotFoundException;

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

	public Squadra update(Squadra squadraAggiornata) {
        // Verifica se la squadra esiste già nel database
        Optional<Squadra> optionalSquadra = squadraRepository.findById(squadraAggiornata.getId());
        
        if (optionalSquadra.isPresent()) {
            Squadra squadraEsistente = optionalSquadra.get();
            
            // Aggiorna i campi della squadra esistente con i nuovi dati
            squadraEsistente.setNome(squadraAggiornata.getNome());
            squadraEsistente.setFondazione(squadraAggiornata.getFondazione());
            squadraEsistente.setIndirizzoSede(squadraAggiornata.getIndirizzoSede());
            squadraEsistente.setPresidente(squadraAggiornata.getPresidente());
            
            // Salva la squadra aggiornata nel database
            return squadraRepository.save(squadraEsistente);
        } else {
            throw new EntityNotFoundException("Squadra non trovata con id: " + squadraAggiornata.getId());
        }
    }
}
