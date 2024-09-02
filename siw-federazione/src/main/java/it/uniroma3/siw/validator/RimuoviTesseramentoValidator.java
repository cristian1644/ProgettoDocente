package it.uniroma3.siw.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.repository.TesseramentoGiocatoreRepository;
import it.uniroma3.siw.repository.UserRepository;

@Component
public class RimuoviTesseramentoValidator implements Validator{

	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired SquadraRepository squadraRepository;
	@Autowired UserRepository userRepository;
	@Autowired CredentialsRepository credentialsRepository;
	@Autowired TesseramentoGiocatoreRepository tesseramentoGiocatoreRepository;
	
	
	@Override
    public void validate(Object o, Errors errors) {
    	TesseramentoGiocatoreDTO dto = (TesseramentoGiocatoreDTO) o;

    	System.out.println("id tessermaneto: " + dto.getTesseramentoId());
    	
    	//recupero il tesseramento
    	Optional<TesseramentoGiocatore> tesseramentoOpt = tesseramentoGiocatoreRepository.findById(dto.getTesseramentoId());
    	TesseramentoGiocatore tesseramento = tesseramentoOpt.get();
    	
    	System.out.println("id squadra: " + tesseramento.getSquadra().getId());
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Credentials c = this.credentialsRepository.findByUsername(username);
            Utente presidente = this.userRepository.findByCredentials(c);
            System.out.println("Utente corrente: " + username);
            System.out.println("Presidente: " + presidente.getCognome());
            boolean result = this.squadraRepository.existsByIdAndPresidente(tesseramento.getSquadra().getId(), presidente);
            System.out.println("Esiste presidente: " + result);
            if(!result) {
            	errors.reject("wrong.president");
            }
    }
	
	@Override
    public boolean supports(Class<?> clazz) {
        return TesseramentoGiocatoreDTO.class.equals(clazz);
    }
}
