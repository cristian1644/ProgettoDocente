package it.uniroma3.siw.validator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.DTO.TesseramentoGiocatoreDTO;
import it.uniroma3.siw.model.TesseramentoGiocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.repository.TesseramentoGiocatoreRepository;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TesseramentoValidator implements Validator{

	@Autowired
    private TesseramentoGiocatoreRepository tesseramentoGiocatoreRepository;
	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired SquadraRepository squadraRepository;

    @Override
    public void validate(Object o, Errors errors) {
    	TesseramentoGiocatoreDTO dto = (TesseramentoGiocatoreDTO) o;

    	List<TesseramentoGiocatore> tesseramentiEsistenti = tesseramentoGiocatoreRepository.findByGiocatore(
                giocatoreRepository.findById(dto.getGiocatoreId()).orElseThrow()
            );

            for (TesseramentoGiocatore tesseramento : tesseramentiEsistenti) {
                if (isOverlapping(dto, tesseramento)) {
                    errors.reject("tesseramento.overlapping");
                }
            }
    }
    
    private boolean isOverlapping(TesseramentoGiocatoreDTO nuovoTesseramento, TesseramentoGiocatore esistenteTesseramento) {
        LocalDate inizioNuovo = nuovoTesseramento.getInizioTesseramento();
        LocalDate fineNuovo = nuovoTesseramento.getFineTesseramento();

        LocalDate inizioEsistente = esistenteTesseramento.getInizioTesseramento();
        LocalDate fineEsistente = esistenteTesseramento.getFineTesseramento();

        return inizioNuovo.isBefore(fineEsistente) && fineNuovo.isAfter(inizioEsistente);
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return TesseramentoGiocatoreDTO.class.equals(clazz);
    }
}
