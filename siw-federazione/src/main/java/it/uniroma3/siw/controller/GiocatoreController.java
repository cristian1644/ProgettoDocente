package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.repository.TesseramentoGiocatoreRepository;
import it.uniroma3.siw.service.GiocatoreService;

@Controller
public class GiocatoreController {

	@Autowired GiocatoreService giocatoreService;
	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired SquadraRepository squadraRepository;
	@Autowired TesseramentoGiocatoreRepository tesseramentoGiocatoreRepository;
	
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
	    List<TesseramentoGiocatore> tesseramenti = (List<TesseramentoGiocatore>) tesseramentoGiocatoreRepository.findAll();
	    LocalDate today = LocalDate.now();
	    
	    // Filtra solo i tesseramenti correnti
	    List<TesseramentoGiocatore> tesseramentiCorrenti = tesseramenti.stream()
	        .filter(tesseramento -> tesseramento.getFineTesseramento() == null || tesseramento.getFineTesseramento().isAfter(today))
	        .collect(Collectors.toList());
		
		model.addAttribute("tesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
		model.addAttribute("removeTesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
        model.addAttribute("giocatori", giocatoreRepository.findAll());
        model.addAttribute("squadre", squadraRepository.findAll());
        model.addAttribute("tesseramenti", tesseramentiCorrenti);
		return "president-gestioneGiocatori";
	}
	
	@GetMapping("/president/gestioneGiocatori")
	public String gestisciGiocatoriPageGET(Model model) {
		model.addAttribute("tesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
		model.addAttribute("removeTesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
        model.addAttribute("giocatori", giocatoreRepository.findAll());
        model.addAttribute("squadre", squadraRepository.findAll());
        model.addAttribute("tesseramenti", tesseramentoGiocatoreRepository.findAll());
		return "president-gestioneGiocatori";
	}

	
	
}
