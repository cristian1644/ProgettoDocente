package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.PresidenteRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.validator.NuovaSquadraValidator;
import jakarta.validation.Valid;

@Controller
public class SquadraController {

	@Autowired SquadraService squadraService;
	
	@Autowired CredentialsRepository credentialsRepository;
	
	@Autowired UserRepository utenteRepository;
	
	@Autowired SquadraRepository squadraRepository;
	
	@Autowired NuovaSquadraValidator nuovaSquadraValidator;
	
	@Autowired PresidenteRepository presidenteRepository;
	
	
	
	@GetMapping("/squadra/{id}")
	  public String getSquadra(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("squadra", this.squadraService.findById(id));
	    return "squadra.html";
	  }
	
	@GetMapping("/squadra")
	  public String showGiocatori(Model model) {
	    model.addAttribute("squadre", this.squadraService.findAll());
	    return "squadre.html";
	  }
	
	@GetMapping("/squadre")
	public String squadrePage(Model model) {
		model.addAttribute("squadre",this.squadraService.findAll());
		model.addAttribute("squadra", new Squadra());
		return "squadre";
	}
	
	@PostMapping("/admin/gestioneSquadre")
	public String gestioneQuadrePage(Model model) {
		model.addAttribute("squadra", new Squadra());
		// Trova tutte le credenziali con ruolo ROLE_PRESIDENT
        List<Credentials> presidentCredentials = credentialsRepository.findByRole("ROLE_PRESIDENT");
        
        // Trova gli utenti associati a queste credenziali
        List<Utente> presidenti = utenteRepository.findByCredentialsIn(presidentCredentials);
        model.addAttribute("presidenti", presidenti);
        model.addAttribute("squadre",this.squadraService.findAll());
		model.addAttribute("squadra", new Squadra());
        
		return "admin-gestioneSquadre";
	}
	
	@PostMapping("/admin/aggiungiSquadra")
    public String addSquadra(@Valid @ModelAttribute("squadra") Squadra squadra,BindingResult bindingResult, Model model) {
		
		this.nuovaSquadraValidator.validate(squadra, bindingResult);
		if(!bindingResult.hasErrors()) {
			squadraRepository.save(squadra);
			return "redirect:/squadre";
		}
		List<Credentials> presidentCredentials = credentialsRepository.findByRole("ROLE_PRESIDENT");
        
        // Trova gli utenti associati a queste credenziali
        List<Utente> presidenti = utenteRepository.findByCredentialsIn(presidentCredentials);
		model.addAttribute("presidenti", presidenti);
		model.addAttribute("squadra", squadra);
        
        return "admin-gestioneSquadre";
    }
	
	
 
}
