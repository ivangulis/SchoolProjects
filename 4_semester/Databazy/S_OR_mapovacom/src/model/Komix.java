package model;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "komixy")
public class Komix {	
	
	@Column(name = "nazov")
	private String nazov;
	
	@Id
	@Column(name = "cislo_komixu")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cislo_komixu;
	
	@Column(name = "datum_vytvorenia")
	@Type(type="date")
	private Date datum_vytvorenia;
	
	@OneToMany
	@JoinColumn(name="cislo_komixu")
	private List<Strana> strany = new ArrayList<Strana>();
	
	
	public String getNazov() {
		return nazov;
	}
	public void setNazov(String nazov) {
		this.nazov = nazov;
	}
	public int getCislo_komixu() {
		return cislo_komixu;
	}
	public void setCislo_komixu(int cislo_komixu) {
		this.cislo_komixu = cislo_komixu;
	}
	public Date getDatum_vytvorenia() {
		return datum_vytvorenia;
	}
	public void setDatum_vytvorenia(Date datum_vytvorenia) {
		this.datum_vytvorenia = datum_vytvorenia;
	}
	public List<Strana> getStrany() {
		return strany;
	}
	public void setStrany(List<Strana> strany) {
		this.strany = strany;
	}

}
