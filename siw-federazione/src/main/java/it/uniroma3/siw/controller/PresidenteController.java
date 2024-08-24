package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.PresidenteService;

@Controller
public class PresidenteController {

@Autowired PresidenteService presidenteService;
	
	@GetMapping("/presidente/{id}")
	  public String getGiocatore(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("presidente", this.presidenteService.findById(id));
	    return "presidente.html";
	  }
	
	@GetMapping("/presidente")
	  public String showGiocatori(Model model) {
	    model.addAttribute("presidentes", this.presidenteService.findAll());
	    return "presidentes.html";
	  }
}
