package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.SquadraService;

@Controller
public class SquadraController {

	@Autowired SquadraService squadraService;
	
	@GetMapping("/squadra/{id}")
	  public String getGiocatore(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("squadra", this.squadraService.findById(id));
	    return "squadra.html";
	  }
	
	@GetMapping("/squadra")
	  public String showGiocatori(Model model) {
	    model.addAttribute("squadras", this.squadraService.findAll());
	    return "squadras.html";
	  }
}
