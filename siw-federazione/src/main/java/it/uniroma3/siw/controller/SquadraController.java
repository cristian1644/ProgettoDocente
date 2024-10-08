package it.uniroma3.siw.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PresidenteService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TesseramentoGiocatoreService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.validator.ModificaSquadraValidator;
import it.uniroma3.siw.validator.NuovaSquadraValidator;
import jakarta.validation.Valid;

@Controller
public class SquadraController {

	@Autowired SquadraService squadraService;	
	
	@Autowired UserService utenteService;
	
	@Autowired NuovaSquadraValidator nuovaSquadraValidator;
	
	@Autowired PresidenteService presidenteService;
	
	@Autowired TesseramentoGiocatoreService tesseramentoGiocatoreService;
	
	@Autowired ModificaSquadraValidator modificaSquadraValidator;
	
	@Autowired GiocatoreService giocatoreService;
	
	@Autowired CredentialsService credentialsService;
	
	
	
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
	
	@PostMapping("/permessoNegato")
	public String permessoNegatoPage(Model model) {
		model.addAttribute("squadre",this.squadraService.findAll());
		model.addAttribute("squadra", new Squadra());
		return "permessoNegato";
	}
	
	@PostMapping("/admin/gestioneSquadre")
	public String gestioneQuadrePage(Model model) {
		// Trova tutte le credenziali con ruolo ROLE_PRESIDENT
        List<Credentials> presidentCredentials = credentialsService.findByRole("ROLE_PRESIDENT");
        
        // Trova gli utenti associati a queste credenziali
        List<Utente> presidenti = utenteService.findByCredentialsIn(presidentCredentials);
        model.addAttribute("presidenti", presidenti);
        model.addAttribute("squadra", new Squadra());
        model.addAttribute("giocatore", new Giocatore());
        model.addAttribute("squadre",this.squadraService.findAll());
        model.addAttribute("giocatori",this.giocatoreService.findAll());
        
		return "admin-gestioneSquadre";
	}
	
	@PostMapping("/admin/aggiungiSquadra")
    public String addSquadra(@Valid @ModelAttribute("squadra") Squadra squadra,BindingResult bindingResult, Model model) {
		
		this.nuovaSquadraValidator.validate(squadra, bindingResult);
		if(!bindingResult.hasErrors()) {
			squadraService.save(squadra);
			return "redirect:/squadre";
		}
		List<Credentials> presidentCredentials = credentialsService.findByRole("ROLE_PRESIDENT");
        
        // Trova gli utenti associati a queste credenziali
        List<Utente> presidenti = utenteService.findByCredentialsIn(presidentCredentials);
		model.addAttribute("presidenti", presidenti);
		model.addAttribute("squadra", squadra);
		model.addAttribute("squadre",this.squadraService.findAll());
		model.addAttribute("giocatore",new Giocatore());
		model.addAttribute("giocatori",this.giocatoreService.findAll());
        return "admin-gestioneSquadre";
    }
	
	@GetMapping("/squadra/{id}/giocatori")
    public String mostraGiocatori(@PathVariable Long id, Model model) {
        Squadra squadra = squadraService.findById(id);
        // Recupera tutti i tesseramenti per la squadra selezionata
        List<TesseramentoGiocatore> tesseramenti = tesseramentoGiocatoreService.findBySquadra(squadra);

        // Estrai i giocatori dai tesseramenti
        List<Giocatore> giocatori = tesseramenti.stream()
                .map(TesseramentoGiocatore::getGiocatore)
                .filter(giocatore -> giocatore.getTesseramentoCorrente() != null) // Filtra solo quelli con tesseramentoCorrente non null
                .distinct() // Evita duplicati, se necessario
                .collect(Collectors.toList());

        model.addAttribute("squadra", squadra);
        model.addAttribute("giocatori", giocatori);
        return "giocatoriSquadra";
    }
	
	@GetMapping("/admin/modificaSquadra")
    public String mostraFormModifica(@RequestParam("id") Long id, Model model) {
        Squadra squadraSelezionata = squadraService.findById(id);
        model.addAttribute("squadraSelezionata", squadraSelezionata);
        model.addAttribute("squadra", new Squadra());
        List<Credentials> presidentCredentials = credentialsService.findByRole("ROLE_PRESIDENT");
        List<Utente> presidenti = utenteService.findByCredentialsIn(presidentCredentials);
		model.addAttribute("presidenti", presidenti);
		model.addAttribute("squadre",this.squadraService.findAll());
		model.addAttribute("giocatore",new Giocatore());
		model.addAttribute("giocatori",this.giocatoreService.findAll());
        return "admin-gestioneSquadre";
    }
	
	@PostMapping("/admin/aggiornaSquadra")
    public String aggiornaSquadra(@Valid @ModelAttribute("squadraSelezionata") Squadra squadra,BindingResult bindingResult, Model model) {
		this.modificaSquadraValidator.validate(squadra, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			squadraService.update(squadra);
			return "redirect:/squadre";
		}
        
		List<Credentials> presidentCredentials = credentialsService.findByRole("ROLE_PRESIDENT");
        List<Utente> presidenti = utenteService.findByCredentialsIn(presidentCredentials);
		model.addAttribute("presidenti", presidenti);
		model.addAttribute("squadre",this.squadraService.findAll());
		model.addAttribute("squadra", new Squadra());
		model.addAttribute("giocatore",new Giocatore());
		model.addAttribute("giocatori",this.giocatoreService.findAll());
		return "admin-gestioneSquadre";
    }
 
}
