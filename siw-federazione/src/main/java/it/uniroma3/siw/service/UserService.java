package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Optional<Utente> getUser(Long id) {
		Optional<Utente> user = userRepository.findById(id);
		return user;
	}
	
	public void saveUser(Utente user) {
		userRepository.save(user);
	}
	
	public Utente creaUtente(String nome, String cognome) {
		Utente user = new Utente();
		user.setNome(nome);
		user.setCognome(cognome);
		return user;
	}
	
	public Utente findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
	
}
