package model.prvky;

import java.util.ArrayList;
import java.util.List;

public class Kniha {

	/** Kniha m� n�zov */
	private String nazovDiela;

	/** Kniha m� svoje str�nky */
	private List<Strana> strany = new ArrayList<Strana>();

	public String getNazovDiela() {
		return nazovDiela;
	}

	public void setNazovDiela(String n�zovDiela) {
		this.nazovDiela = n�zovDiela;
	}

	public List<Strana> getStrany() {
		return strany;
	}

	public void setStrany(List<Strana> strany) {
		this.strany = strany;
	}

}
