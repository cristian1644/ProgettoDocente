package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.CredentialsValidator;
import jakarta.validation.Valid;

@Controller
public class RegistrazioneController {

	@Autowired private UserService userService;
	@Autowired private CredentialsService credentialsService;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private CredentialsValidator credentialsValidator;
	
	@PostMapping("/registrazione")
    public String registraUtente(@Valid @ModelAttribute("credentials") Credentials credentials, 
            BindingResult bindingResult, @RequestParam String nome, @RequestParam String cognome,
            RedirectAttributes redirectAttributes, Model model) {
    	
		this.credentialsValidator.validate(credentials, bindingResult);
		
		if(!bindingResult.hasErrors()) {
	        Utente user = userService.creaUtente(nome, cognome);
	        this.userService.saveUser(user);
	        
	        String encodedPassword = passwordEncoder.encode(credentials.getPassword()); //codifico la password
	        credentials.setPassword(encodedPassword); //la cripto prima di salvarla nel db
	        credentials.setUser(user);
	        credentials.setRole("ROLE_USER");
	        this.credentialsService.saveCredentials(credentials);
	        
	        redirectAttributes.addFlashAttribute("successMessage", "Registrazione avvenuta con successo!");
	
	        return "redirect:/login";
		}
		model.addAttribute("credentials", credentials);
		
		return "login";
    }
}
