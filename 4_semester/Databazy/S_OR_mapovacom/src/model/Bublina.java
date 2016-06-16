package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bubliny")
public class Bublina {	
	
	@Column(name = "hranata")
	private boolean hranata = false;
	
	@Column(name = "text")
	private String text;
	
	@Id
	@Column(name = "id_bubliny")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_bubliny;
	
	@ManyToOne
	@JoinColumn(name = "id_nakreslenia")
	private Nakreslenie nakreslenie;
	
	public Integer getId_bubliny() {
		return id_bubliny;
	}
	public void setId_bubliny(Integer id_bubliny) {
		this.id_bubliny = id_bubliny;
	}
	
	public Nakreslenie getNakreslenie() {
		return nakreslenie;
	}
	public void setNakreslenie(Nakreslenie nakreslenie) {
		this.nakreslenie = nakreslenie;
	}
	
	public boolean isHranata() {
		return hranata;
	}
	public void setHranata(boolean hranata) {
		this.hranata = hranata;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
