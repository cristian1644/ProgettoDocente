package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.TesseramentoGiocatoreService;

@Controller
public class TesseramentoGiocatoreController {

@Autowired TesseramentoGiocatoreService tesseramentoGiocatoreService;
	
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
}
