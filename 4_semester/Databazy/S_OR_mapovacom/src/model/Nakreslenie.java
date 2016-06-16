package model;


import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "postavicka_nakreslena")
public class Nakreslenie {		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_nakreslenia")
	private Integer id_nakreslenia;	
	
	@Column(name = "datum_nakreslenia")
	@Type(type="date")
	private Date datum_nakreslenia;	
	
	@Column(name = "cinnost")
	private String cinnost;
	
	@ManyToOne
	@JoinColumn(name = "id_postavy")
	private Postava postava; //id_postavy
	
	@ManyToOne
	@JoinColumn(name = "id_strany")
	private Strana strana; //id_strany	
	
	@OneToMany
	@JoinColumn(name="id_nakreslenia")
	private List<Bublina> bubliny = new ArrayList<Bublina>();
	
	public List<Bublina> getBubliny() {
		return bubliny;
	}
	public void setBubliny(List<Bublina> bubliny) {
		this.bubliny = bubliny;
	}
	public Integer getId_nakreslenia() {
		return id_nakreslenia;
	}
	public void setId_nakreslenia(Integer id_nakreslenia) {
		this.id_nakreslenia = id_nakreslenia;
	}
	public Date getDatum_nakreslenia() {
		return datum_nakreslenia;
	}
	public void setDatum_nakreslenia(Date datum_nakreslenia) {
		this.datum_nakreslenia = datum_nakreslenia;
	}
	public String getCinnost() {
		return cinnost;
	}
	public void setCinnost(String cinnost) {
		this.cinnost = cinnost;
	}
	public Postava getPostava() {
		return postava;
	}
	public void setPostava(Postava postava) {
		this.postava = postava;
	}
	public Strana getStrana() {
		return strana;
	}
	public void setStrana(Strana strana) {
		this.strana = strana;
	}
			
	

}
