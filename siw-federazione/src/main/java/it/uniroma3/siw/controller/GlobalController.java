package it.uniroma3.siw.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

	
	@ModelAttribute
    public void addGlobalAttributes(Model model, Authentication authentication) {
		
        model.addAttribute("authentication", authentication);
    }
	
}
