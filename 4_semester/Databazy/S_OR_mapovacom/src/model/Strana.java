package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "strany")
public class Strana {		

	@Column(name = "situacia")
	private String situacia;
	
	@Id
	@Column(name = "id_strany")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_strany;
	
	@ManyToOne
	@JoinColumn(name = "cislo_komixu")
	private Komix komix;
	
	@OneToMany
	@JoinColumn(name="id_strany")
	private List<Nakreslenie> nakreslenia = new ArrayList<Nakreslenie>();

	public List<Nakreslenie> getNakreslenia() {
		return nakreslenia;
	}

	public void setNakreslenia(List<Nakreslenie> nakreslenia) {
		this.nakreslenia = nakreslenia;
	}

	public int getId_strany() {
		return id_strany;
	}

	public void setId_strany(int id_strany) {
		this.id_strany = id_strany;
	}

	public Komix getKomix() {
		return komix;
	}

	public void setKomix(Komix komix) {
		this.komix = komix;
	}

	public String getSituacia() {
		return situacia;
	}

	public void setSituacia(String situacia) {
		this.situacia = situacia;
	}

}
