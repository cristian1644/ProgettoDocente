package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TesseramentoGiocatoreService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.AggiungiGiocatoreValidator;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {

	@Autowired GiocatoreService giocatoreService;
	@Autowired TesseramentoGiocatoreService tesseramentoGiocatoreService;
	@Autowired AggiungiGiocatoreValidator aggiungiGiocatoreValidator;
	@Autowired CredentialsService credentialsService;
	@Autowired UserService utenteService;
	@Autowired SquadraService squadraService;
	
	@GetMapping("/giocatore/{id}")
	  public String getGiocatore(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("giocatore", this.giocatoreService.findById(id));
	    return "giocatore.html";
	  }
	
	@GetMapping("/giocatori")
	  public String showGiocatori(Model model) {
	    model.addAttribute("players", this.giocatoreService.findAll());
	    return "giocatori";
	  }

	@PostMapping("/president/gestioneGiocatori")
	public String gestisciGiocatoriPagePOST(Model model) {
		// Recupera tutti i tesseramenti e filtra quelli correnti
	    List<TesseramentoGiocatore> tesseramenti = (List<TesseramentoGiocatore>) tesseramentoGiocatoreService.findAll();
	    LocalDate today = LocalDate.now();
	    
	    // Filtra solo i tesseramenti correnti
	    List<TesseramentoGiocatore> tesseramentiCorrenti = tesseramenti.stream()
	        .filter(tesseramento -> tesseramento.getFineTesseramento() == null || tesseramento.getFineTesseramento().isAfter(today))
	        .collect(Collectors.toList());
		
		model.addAttribute("tesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
		model.addAttribute("removeTesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
        model.addAttribute("giocatori", giocatoreService.findAll());
        model.addAttribute("squadre", squadraService.findAll());
        model.addAttribute("tesseramenti", tesseramentiCorrenti);
		return "president-gestioneGiocatori";
	}
	
	@GetMapping("/president/gestioneGiocatori")
	public String gestisciGiocatoriPageGET(Model model) {
		model.addAttribute("tesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
		model.addAttribute("removeTesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
        model.addAttribute("giocatori", giocatoreService.findAll());
        model.addAttribute("squadre", squadraService.findAll());
        model.addAttribute("tesseramenti", tesseramentoGiocatoreService.findAll());
		return "president-gestioneGiocatori";
	}

	@PostMapping("/admin/aggiungiGiocatore")
	public String addGiocatore(@Valid @ModelAttribute("giocatore") Giocatore giocatore,BindingResult bindingResult, Model model) {
		
		this.aggiungiGiocatoreValidator.validate(giocatore, bindingResult);
		
		if(bindingResult.hasErrors()) {
			List<Credentials> presidentCredentials = credentialsService.findByRole("ROLE_PRESIDENT");        
	        // Trova gli utenti associati a queste credenziali
	        List<Utente> presidenti = utenteService.findByCredentialsIn(presidentCredentials);
			model.addAttribute("presidenti", presidenti);
	        model.addAttribute("squadra", new Squadra());
	        model.addAttribute("giocatore", giocatore);
	        model.addAttribute("squadre", this.squadraService.findAll());
	        model.addAttribute("giocatori", this.giocatoreService.findAll());
			return "admin-gestioneSquadre";
		}
		// Ottieni la data di nascita come stringa dal form
	    String dataNascitaString = giocatore.getDataNascita();
	    // Converti la stringa in LocalDate
	    LocalDate dataNascitaLocalDate = LocalDate.parse(dataNascitaString);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String dataNascitaFormattata = dataNascitaLocalDate.format(formatter);
	    giocatore.setDataNascita(dataNascitaFormattata);
		this.giocatoreService.save(giocatore);
		model.addAttribute("players", this.giocatoreService.findAll());
		return "redirect:/giocatori";
	}
	
	@PostMapping("/admin/rimuoviGiocatore")
	public String removeGiocatore(@RequestParam("giocatoreId") Long giocatoreId, Model model) {
	    // Trova il giocatore per ID
	    Giocatore giocatore = giocatoreService.findById(giocatoreId);

	    // Rimuovi il giocatore dal database
	    giocatoreService.delete(giocatore);

	    // Aggiorna la lista dei giocatori e restituiscila al modello
	    model.addAttribute("players", giocatoreService.findAll());
	    return "redirect:/giocatori";
	}
}
