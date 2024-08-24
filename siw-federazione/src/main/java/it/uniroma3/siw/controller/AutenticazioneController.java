package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;

@Controller
public class AutenticazioneController {

	@GetMapping("/login")
    public String loginPage(Model model) {
		model.addAttribute("credentials", new Credentials());
        return "login";
    }
	
	@GetMapping("/")
    public String index() {    
        return "index"; 
    }
}
