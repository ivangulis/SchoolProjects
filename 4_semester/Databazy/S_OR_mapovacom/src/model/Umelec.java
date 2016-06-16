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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "umelci")
public class Umelec {	
	
	@Column(name = "meno")
	private String meno;
	
	@Column(name = "zamestnany_od")
	@Type(type="date")
	private Date zamestnany_od;
	
	@Id
	@Column(name = "id_zamestnanca")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_zamestnanca;
	
	@OneToMany
	@JoinColumn(name="id_autora") //musí byt autora
	private List<Postava> postavy = new ArrayList<Postava>();

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public Date getZamestnany_od() {
		return zamestnany_od;
	}

	public void setZamestnany_od(Date datum) {
		this.zamestnany_od = datum;
	}

	public int getId_zamestnanca() {
		return id_zamestnanca;
	}

	public void setId_zamestnanca(int id_zamestnanca) {
		this.id_zamestnanca = id_zamestnanca;
	}

	public List<Postava> getPostavy() {
		return postavy;
	}

	public void setPostavy(List<Postava> postavy) {
		this.postavy = postavy;
	}

}
