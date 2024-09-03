package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Giocatore {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String nome;
	private String cognome;
	private String dataNascita;
	private String luogoNascita;
	private String ruolo;
	
	@OneToOne
	@JoinColumn(name = "tesseramento_corrente_id")
	private TesseramentoGiocatore tesseramentoCorrente;
	
	@OneToMany(mappedBy = "giocatore", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TesseramentoGiocatore> tesseramentiPassati = new ArrayList<>();
	
	//setter e getter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public TesseramentoGiocatore getTesseramentoCorrente() {
		return tesseramentoCorrente;
	}
	public void setTesseramentoCorrente(TesseramentoGiocatore tesseramentoCorrente) {
		this.tesseramentoCorrente = tesseramentoCorrente;
	}
	public List<TesseramentoGiocatore> getTesseramentiPassati() {
		return tesseramentiPassati;
	}
	public void setTesseramentiPassati(List<TesseramentoGiocatore> tesseramentiPassati) {
		this.tesseramentiPassati = tesseramentiPassati;
	}
	//equals e hashcode
	@Override
	public int hashCode() {
		return Objects.hash(cognome, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome);
	}
	
	

}
