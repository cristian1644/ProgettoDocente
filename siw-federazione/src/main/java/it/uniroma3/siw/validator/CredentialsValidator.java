package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator{

	@Autowired private CredentialsService credentialsService;
	
	@Override
	  public void validate(Object o, Errors errors) {
	    Credentials credentials = (Credentials)o;
	    
	    //controllo se la mail è già in uso
	    if (credentials.getEmail() != null && credentialsService.findByEmail(credentials.getEmail()) != null) {
            errors.rejectValue("email", "email.unique");
        }
	    
	    //controllo se la mail inserita è vailda
	    if (credentials.getEmail() != null && !credentials.getEmail().contains("@gmail.com")) {
            errors.rejectValue("email", "email.invalid");
        }
	    
	    //controllo se lo username scelto è libero
	    if(credentials.getUsername() != null && credentialsService.findByUsername(credentials.getUsername()) != null) {
	    	errors.rejectValue("username", "username.unique");
	    }
	    
	  }
	  
	  @Override
	    public boolean supports(Class<?> aClass) {
	      return Credentials.class.equals(aClass);
	    }
}
