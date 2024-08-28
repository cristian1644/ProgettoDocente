package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.CredentialsValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	
	@GetMapping("/user")
    public String user(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
        	model.addAttribute("authentication", authentication);
            String username = authentication.getName();
            Credentials credentials = credentialsService.findByUsername(username);
            if (credentials != null) {
                model.addAttribute("credentials", credentials);
                Utente user = userService.findByCredentials(credentials);
                model.addAttribute("user", user);
            } 
        } 
        return "user";
    }
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null) {
	        new SecurityContextLogoutHandler().logout(request, response, authentication);
	    }
	    return "redirect:/";
	}
}
