package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TesseramentoGiocatoreService;
import it.uniroma3.siw.validator.NuovoTesseramentoValidator;
import it.uniroma3.siw.validator.RimuoviTesseramentoValidator;
import jakarta.validation.Valid;

@Controller
public class TesseramentoGiocatoreController {

	@Autowired TesseramentoGiocatoreService tesseramentoGiocatoreService;
	@Autowired GiocatoreService giocatoreService;
	@Autowired NuovoTesseramentoValidator nuovoTesseramentoValidator;
	@Autowired RimuoviTesseramentoValidator rimuoviTesseramentoValidator;
	@Autowired SquadraService squadraService;
	
	
	 @PostMapping("/president/gestioneGiocatori/salva-tesseramento")
	    public String salvaTesseramento(@Valid @ModelAttribute("tesseramentoGiocatoreDTO") TesseramentoGiocatoreDTO dto, BindingResult bindingResult,Model model) {
		 
		 this.nuovoTesseramentoValidator.validate(dto, bindingResult);
		 
		 if (bindingResult.hasErrors()) {
			 	model.addAttribute("giocatori", giocatoreService.findAll());
		        model.addAttribute("squadre", squadraService.findAll());
		     // Recupera tutti i tesseramenti e filtra quelli correnti
			    List<TesseramentoGiocatore> tesseramenti = (List<TesseramentoGiocatore>) tesseramentoGiocatoreService.findAll();
			    LocalDate today = LocalDate.now();
			    
			    // Filtra solo i tesseramenti correnti
			    List<TesseramentoGiocatore> tesseramentiCorrenti = tesseramenti.stream()
			        .filter(tesseramento -> tesseramento.getFineTesseramento() == null || tesseramento.getFineTesseramento().isAfter(today))
			        .collect(Collectors.toList());
		        model.addAttribute("tesseramenti", tesseramentiCorrenti);
		        model.addAttribute("removeTesseramentoGiocatoreDTO", tesseramentoGiocatoreService.findAll());
	            return "president-gestioneGiocatori";
	        }
		 
	        TesseramentoGiocatore tesseramentoGiocatore = new TesseramentoGiocatore(
	        		giocatoreService.findById(dto.getGiocatoreId()),
	        		squadraService.findById(dto.getSquadraId()),
	        		dto.getInizioTesseramento(),
	        		dto.getFineTesseramento());

	        tesseramentoGiocatoreService.save(tesseramentoGiocatore);
	        tesseramentoGiocatore.getGiocatore().setTesseramentoCorrente(tesseramentoGiocatore);
	        giocatoreService.save(tesseramentoGiocatore.getGiocatore());
	        
	        model.addAttribute("squadre",this.squadraService.findAll());
			model.addAttribute("squadra", new Squadra());
			return "redirect:/squadre";
	    }
	 
	 @PostMapping("/president/gestioneGiocatori/rimuovi-tesseramento")
	 public String rimuoviTesseramento(@Valid @ModelAttribute("removeTesseramentoGiocatoreDTO") TesseramentoGiocatoreDTO dto, 
	                                   BindingResult bindingResult, Model model) {
		 
	     this.rimuoviTesseramentoValidator.validate(dto, bindingResult);
	     model.addAttribute("tesseramentoGiocatoreDTO", new TesseramentoGiocatoreDTO());
	     model.addAttribute("giocatori", giocatoreService.findAll());
         model.addAttribute("squadre", squadraService.findAll());
      // Recupera tutti i tesseramenti
 	    List<TesseramentoGiocatore> tesseramenti = (List<TesseramentoGiocatore>) tesseramentoGiocatoreService.findAll();
 	    LocalDate today = LocalDate.now();
 	    
 	    // Filtra solo i tesseramenti correnti
 	    List<TesseramentoGiocatore> tesseramentiCorrenti = tesseramenti.stream()
 	        .filter(tesseramento -> tesseramento.getFineTesseramento() == null || tesseramento.getFineTesseramento().isAfter(today))
 	        .collect(Collectors.toList());
         model.addAttribute("tesseramenti", tesseramentiCorrenti);

         if(bindingResult.hasErrors()){
        	 return "president-gestioneGiocatori";
         }
         
       //prendo il tesseramneto che devo rimuovere
         Optional<TesseramentoGiocatore> tesseramentoOpt = tesseramentoGiocatoreService.findById(dto.getTesseramentoId());
         TesseramentoGiocatore tesseramentoPassato = tesseramentoOpt.get();
         System.out.println("id tesseramento: " + tesseramentoPassato.getId());
         tesseramentoPassato.setFineTesseramento(LocalDate.now()); //imposto la data di fine a oggi
         
         //prendo il giocatore che deve essere tolto dalla squadra
         Giocatore giocatore = tesseramentoPassato.getGiocatore();
         //il tesseramento corrente deve terminare, quindi va nella lista di quelli passati
         giocatore.getTesseramenti().add(tesseramentoPassato);
         //il tesseramento corrente deve essere rimosso
         giocatore.setTesseramentoCorrente(null);
         giocatoreService.save(giocatore); //aggiorno le modifiche del giocatore
	     tesseramentoGiocatoreService.save(tesseramentoPassato); //aggiorno le modifiche del tesseramento passato
	     model.addAttribute("successMessage", "Tesseramento rimosso con successo.");

	     return "president-gestioneGiocatori";
	 }
	 
	 @GetMapping("/giocatore/{id}/storico-tesseramenti")
	 public String storicoTesseramentiPage(@PathVariable Long id, Model model) {
		 Giocatore giocatore = giocatoreService.findById(id);
		 //recupero tutti i tesseramenti del giocatore
		 List<TesseramentoGiocatore> tuttiTesseramenti = giocatore.getTesseramenti();
		 // escludo quello corrente
		 List<TesseramentoGiocatore> storicoTesseramenti = tuttiTesseramenti.stream()
			        .filter(tesseramento -> !tesseramento.equals(giocatore.getTesseramentoCorrente()))
			        .collect(Collectors.toList());
	    
		 model.addAttribute("giocatore", giocatore);
		 model.addAttribute("storicoTesseramenti", storicoTesseramenti);
		 return "giocatori-storicoTesseramenti";
	 }
}
