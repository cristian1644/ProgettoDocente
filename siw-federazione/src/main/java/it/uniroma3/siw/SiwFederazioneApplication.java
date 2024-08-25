package it.uniroma3.siw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import it.uniroma3.siw.service.PasswordUpdaterService;

@SpringBootApplication
public class SiwFederazioneApplication {

	@Autowired
    private PasswordUpdaterService passwordUpdaterService;
	
	public static void main(String[] args) {
		SpringApplication.run(SiwFederazioneApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
    public void updatePasswordsOnStartup() {
        passwordUpdaterService.updatePasswords();
    }
	
	
}
