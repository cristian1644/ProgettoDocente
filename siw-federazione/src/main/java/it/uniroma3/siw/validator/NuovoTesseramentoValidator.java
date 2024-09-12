package it.uniroma3.siw.validator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.repository.UserRepository;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NuovoTesseramentoValidator implements Validator{

	
	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired SquadraRepository squadraRepository;
	@Autowired UserRepository userRepository;
	@Autowired CredentialsRepository credentialsRepository;
	
    @Override
    public void validate(Object o, Errors errors) {
    	TesseramentoGiocatoreDTO dto = (TesseramentoGiocatoreDTO) o;

    	//recupero il giocatore e il suo tesseramneto corrente
    	 Giocatore giocatore = giocatoreRepository.findById(dto.getGiocatoreId()).orElseThrow();
    	// Recupera il tesseramento corrente del giocatore e quelli passati
    	    List<TesseramentoGiocatore> tesseramenti = giocatore.getTesseramenti();

            //controllo anche i tesseramenti passati
    	    for (TesseramentoGiocatore tesseramento : tesseramenti) {
    	        if (isOverlapping(dto, tesseramento)) {
    	            errors.rejectValue("inizioTesseramento", "tesseramento.overlapping");
    	            break;  // Esci dal loop se trovi una sovrapposizione
    	        }
    	    }
    	    
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Credentials c = this.credentialsRepository.findByUsername(username);
            Utente presidente = this.userRepository.findByCredentials(c);
            System.out.println("Utente corrente: " + username);
            System.out.println("Presidente: " + presidente);
            boolean result = this.squadraRepository.existsByIdAndPresidente(dto.getSquadraId(), presidente);
            System.out.println("Esiste presidente: " + result);
            if(!result) {
            	errors.reject("wrong.president");
            }
    }
    
    private boolean isOverlapping(TesseramentoGiocatoreDTO nuovoTesseramento, TesseramentoGiocatore esistenteTesseramento) {
        LocalDate inizioNuovo = nuovoTesseramento.getInizioTesseramento();
        LocalDate fineNuovo = nuovoTesseramento.getFineTesseramento();

        LocalDate inizioEsistente = esistenteTesseramento.getInizioTesseramento();
        LocalDate fineEsistente = esistenteTesseramento.getFineTesseramento();

        return inizioNuovo.isBefore(fineEsistente) && fineNuovo.isAfter(inizioEsistente);
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return TesseramentoGiocatoreDTO.class.equals(clazz);
    }
}
