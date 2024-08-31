package it.uniroma3.siw.DTO;

import java.time.LocalDate;

public class TesseramentoGiocatoreDTO {

	private Long tesseramentoId;
	private Long giocatoreId;
    private Long squadraId;
    private LocalDate inizioTesseramento;
    private LocalDate fineTesseramento;
    
    
	public Long getGiocatoreId() {
		return giocatoreId;
	}
	public void setGiocatoreId(Long giocatoreId) {
		this.giocatoreId = giocatoreId;
	}
	public Long getSquadraId() {
		return squadraId;
	}
	public void setSquadraId(Long squadraId) {
		this.squadraId = squadraId;
	}
	public LocalDate getInizioTesseramento() {
		return inizioTesseramento;
	}
	public void setInizioTesseramento(LocalDate inizioTesseramento) {
		this.inizioTesseramento = inizioTesseramento;
	}
	public LocalDate getFineTesseramento() {
		return fineTesseramento;
	}
	public void setFineTesseramento(LocalDate fineTesseramento) {
		this.fineTesseramento = fineTesseramento;
	}
	 public Long getTesseramentoId() {
	        return tesseramentoId;
	    }
	public void setTesseramentoId(Long tesseramentoId) {
	    this.tesseramentoId = tesseramentoId;
	}
    
    
}
