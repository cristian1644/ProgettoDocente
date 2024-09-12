package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.SquadraService;

@Component
public class ModificaSquadraValidator implements Validator{

	@Autowired SquadraService squadraService;
	@Autowired SquadraRepository squadraRepository;
	
	@Override
	  public void validate(Object o, Errors errors) {
	    Squadra squadra = (Squadra)o;
	    
	    //controllo se l'anno di fondazione è valido
	    if ( (squadra.getFondazione() < 1870 || squadra.getFondazione() > 2024) ) {
        errors.rejectValue("fondazione", "fondazione.invalid");
    }
	    
	    //controllo se l'indirizzo è valido
	    if(squadra.getIndirizzoSede() != null && !squadra.getIndirizzoSede().startsWith("via")) {
	    	errors.rejectValue("indirizzoSede", "sede.invalid");
	    }
	    //controllo se la squadra esiste già, escludendo quella selezionata
	    Squadra existingSquadra = squadraRepository.findByNomeAndIdNot(squadra.getNome(), squadra.getId());
        if (existingSquadra != null) {
        	errors.rejectValue("nome", "nome.unique");
        }
	    
	  }
	  
	  @Override
	    public boolean supports(Class<?> aClass) {
	      return Squadra.class.equals(aClass);
	    }
}
