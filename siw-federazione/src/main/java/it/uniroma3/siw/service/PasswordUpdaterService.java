package it.uniroma3.siw.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
import jakarta.transaction.Transactional;

@Service
public class PasswordUpdaterService {

	@Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePasswords() {
    	Iterable<Credentials> iterable = credentialsRepository.findAll();
    	List<Credentials> credentials = StreamSupport.stream(iterable.spliterator(), false)
    	                                              .collect(Collectors.toList());

        for (Credentials cred : credentials) {
            if (!cred.getPassword().startsWith("$2a$")) { // Verifica se la password è già criptata
                String encodedPassword = passwordEncoder.encode(cred.getPassword());
                cred.setPassword(encodedPassword);
                credentialsRepository.save(cred); // Salva la password criptata
            }
        }
    }
}
