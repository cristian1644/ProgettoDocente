package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.repository.TesseramentoGiocatoreRepository;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TesseramentoGiocatoreService;
import it.uniroma3.siw.validator.NuovoTesseramentoValidator;
import it.uniroma3.siw.validator.RimuoviTesseramentoValidator;
import jakarta.validation.Valid;

@Controller
public class TesseramentoGiocatoreController {

	@Autowired TesseramentoGiocatoreService tesseramentoGiocatoreService;
	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired SquadraRepository squadraRepository;
	@Autowired TesseramentoGiocatoreRepository tesseramentoGiocatoreRepository;
	@Autowired NuovoTesseramentoValidator nuovoTesseramentoValidator;
	@Autowired RimuoviTesseramentoValidator rimuoviTesseramentoValidator;
	@Autowired SquadraService squadraService;
	
	
	@GetMapping("/tesseramentoGiocatore/{id}")
	  public String getTesseramentoGiocatore(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("tesseramentoGiocatore", this.tesseramentoGiocatoreService.findById(id));
	    return "tesseramentoGiocatore.html";
	  }
	
	@GetMapping("/tesseramentoGiocatore")
	  public String showGiocatori(Model model) {
	    model.addAttribute("tesseramentoGiocatores", this.tesseramentoGiocatoreService.findAll());
	    return "tesseramentoGiocatores.html";
	  }
	
	 @PostMapping("/president/gestioneGiocatori/salva-tesseramento")
	    public String salvaTesseramento(@Valid @ModelAttribute("tesseramentoGiocatoreDTO") TesseramentoGiocatoreDTO dto, BindingResult bindingResult,Model model) {
		 
		 this.nuovoTesseramentoValidator.validate(dto, bindingResult);
		 
		 if (bindingResult.hasErrors()) {
			 	model.addAttribute("giocatori", giocatoreRepository.findAll());
		        model.addAttribute("squadre", squadraRepository.findAll());
		        model.addAttribute("tesseramenti", tesseramentoGiocatoreRepository.findAll());
	            return "president-gestioneGiocatori";
	        }
		 
	        TesseramentoGiocatore tesseramentoGiocatore = new TesseramentoGiocatore();
	        tesseramentoGiocatore.setGiocatore(giocatoreRepository.findById(dto.getGiocatoreId()).orElseThrow());
	        tesseramentoGiocatore.setSquadra(squadraRepository.findById(dto.getSquadraId()).orElseThrow());
	        tesseramentoGiocatore.setInizioTesseramento(dto.getInizioTesseramento());
	        tesseramentoGiocatore.setFineTesseramento(dto.getFineTesseramento());

	        tesseramentoGiocatoreRepository.save(tesseramentoGiocatore);
	        
	        model.addAttribute("squadre",this.squadraService.findAll());
			model.addAttribute("squadra", new Squadra());
			return "redirect:/squadre";
	    }
	 
	 @PostMapping("/president/gestioneGiocatori/rimuovi-tesseramento")
	 public String rimuoviTesseramento(@Valid @ModelAttribute("removeTesseramentoGiocatoreDTO") TesseramentoGiocatoreDTO dto, 
	                                   BindingResult bindingResult, Model model) {
		 
		 
	     // Validazione custom
	     this.rimuoviTesseramentoValidator.validate(dto, bindingResult);
	     
	     if (bindingResult.hasErrors()) {
	         model.addAttribute("giocatori", giocatoreRepository.findAll());
	         model.addAttribute("squadre", squadraRepository.findAll());
	         model.addAttribute("tesseramenti", tesseramentoGiocatoreRepository.findAll());
	         model.addAttribute("tesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
	         return "president-gestioneGiocatori";
	     }

	     // Rimozione del tesseramento se non ci sono errori
	     tesseramentoGiocatoreRepository.deleteById(dto.getTesseramentoId());
	     model.addAttribute("successMessage", "Tesseramento rimosso con successo.");

	     return "president-gestioneGiocatori";
	 }
}
