package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.service.GiocatoreService;

@Component
public class AggiungiGiocatoreValidator implements Validator{

	@Autowired GiocatoreService giocatoreService;
	
	@Override
    public void validate(Object o, Errors errors) {
    	Giocatore giocatore = (Giocatore) o;
    	
    	if(this.giocatoreService.giocatoreEsiste(giocatore.getNome(), giocatore.getCognome())) {
    		errors.rejectValue("cognome","giocatore.duplicate");
    	}
    	
    }
	
	@Override
    public boolean supports(Class<?> clazz) {
        return Giocatore.class.equals(clazz);
    }
}
