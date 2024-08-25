package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Squadra {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String nome;
	private int fondazione;
	private String indirizzoSede;
	
	@OneToMany(mappedBy = "squadra")
	private List<TesseramentoGiocatore> giocatori;
	
	@ManyToOne
    @JoinColumn(name = "presidente_id")
    private Utente presidente;
	
	//getter e setter
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
	public int getFondazione() {
		return fondazione;
	}
	public void setFondazione(int fondazione) {
		this.fondazione = fondazione;
	}
	public String getIndirizzoSede() {
		return indirizzoSede;
	}
	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}
	public List<TesseramentoGiocatore> getGiocatori() {
		return giocatori;
	}
	public void setTesseramentoGiocatore(List<TesseramentoGiocatore> giocatori) {
		this.giocatori = giocatori;
	}
	
	public Utente getPresidente() {
		return presidente;
	}
	public void setPresidente(Utente presidente) {
		this.presidente = presidente;
	}
	//equals e hashcode
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(nome, other.nome);
	}
	
	
}
