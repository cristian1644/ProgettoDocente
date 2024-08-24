package it.uniroma3.siw.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@JoinColumn(name = "giocatore_id")
	@ManyToOne
	private Giocatore giocatore;
	
	@JoinColumn(name = "squadra_id")
	@ManyToOne
	private Squadra squadra;
	private String inizioTesseramento;
	private String fineTesseramento;
	
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
	public String getInizioTesseramento() {
		return inizioTesseramento;
	}
	public void setInizioTesseramento(String inizioTesseramento) {
		this.inizioTesseramento = inizioTesseramento;
	}
	public String getFineTesseramento() {
		return fineTesseramento;
	}
	public void setFineTesseramento(String fineTesseramento) {
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
