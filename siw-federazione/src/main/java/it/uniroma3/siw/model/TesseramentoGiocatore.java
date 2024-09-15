package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TesseramentoGiocatore {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@JoinColumn(name = "giocatore_id")
	@ManyToOne
	private Giocatore giocatore;
	
	@JoinColumn(name = "squadra_id")
	@ManyToOne
	private Squadra squadra;
	private LocalDate inizioTesseramento;
	private LocalDate fineTesseramento;
	
	public TesseramentoGiocatore(Giocatore giocatore, Squadra squadra, LocalDate inizioTesseramento, LocalDate fineTesseramento) {
		this.giocatore = giocatore;
		this.squadra = squadra;
		this.inizioTesseramento = inizioTesseramento;
		this.fineTesseramento = fineTesseramento;
	}
	
	public TesseramentoGiocatore() {
		
	}
	
	//setter e getter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Giocatore getGiocatore() {
		return giocatore;
	}
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}
	public Squadra getSquadra() {
		return squadra;
	}
	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
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
		
		//equals e hashcode
	}
	@Override
	public int hashCode() {
		return Objects.hash(fineTesseramento, giocatore, inizioTesseramento, squadra);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TesseramentoGiocatore other = (TesseramentoGiocatore) obj;
		return Objects.equals(fineTesseramento, other.fineTesseramento) && Objects.equals(giocatore, other.giocatore)
				&& Objects.equals(inizioTesseramento, other.inizioTesseramento)
				&& Objects.equals(squadra, other.squadra);
	}
	
	
}
