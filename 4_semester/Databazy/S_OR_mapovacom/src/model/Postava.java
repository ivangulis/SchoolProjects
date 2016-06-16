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
@Table(name = "postavy")
public class Postava {					

	@Column(name = "meno")
	private String meno;
	
	@Column(name = "typ")
	private String typ;
	
	@Column(name = "muz")
	private boolean muz;
	
	@Id
	@Column(name = "id_postavy")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_postavy;
	
	@ManyToOne
	@JoinColumn(name = "id_autora")  //musÌ byù autora
	private Umelec umelec;
	
	@OneToMany
	@JoinColumn(name="id_postavy")
	private List<Nakreslenie> nakreslenia = new ArrayList<Nakreslenie>();
	
	
	public List<Nakreslenie> getNakreslenia() {
		return nakreslenia;
	}
	public void setNakreslenia(List<Nakreslenie> nakreslenia) {
		this.nakreslenia = nakreslenia;
	}
	public Umelec getUmelec() {
		return umelec;
	}
	public void setUmelec(Umelec umelec) {
		this.umelec = umelec;
	}
	
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	public boolean isMuz() {
		return muz;
	}
	public void setMuz(boolean muz) {
		this.muz = muz;
	}
	
	public Integer getId_postavy() {
		return id_postavy;
	}
	public void setId_postavy(Integer id_postavy) {
		this.id_postavy = id_postavy;
	}
	
	
	

}
