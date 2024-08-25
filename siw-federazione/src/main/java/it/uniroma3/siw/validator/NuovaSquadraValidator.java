package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.service.SquadraService;

@Component
public class NuovaSquadraValidator implements Validator{

	@Autowired SquadraService squadraService;
	
	@Override
	  public void validate(Object o, Errors errors) {
	    Squadra squadra = (Squadra)o;
	    
	    //controllo se la squadra è gia registrata
	    if (squadra.getNome() != null && squadraService.findByNome(squadra.getNome()) != null) {
          errors.rejectValue("nome", "nome.unique");
      }
	    
	    //controllo se l'anno di fondazione è valido
	    if ( (squadra.getFondazione() < 1870 || squadra.getFondazione() > 2024) ) {
          errors.rejectValue("fondazione", "fondazione.invalid");
      }
	    
	    //controllo se lo username scelto è libero
	    if(squadra.getIndirizzoSede() != null && !squadra.getIndirizzoSede().startsWith("via")) {
	    	errors.rejectValue("indirizzoSede", "sede.invalid");
	    }
	    
	  }
	  
	  @Override
	    public boolean supports(Class<?> aClass) {
	      return Squadra.class.equals(aClass);
	    }
}
