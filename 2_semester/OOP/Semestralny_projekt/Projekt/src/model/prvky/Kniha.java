package model.prvky;

import java.util.ArrayList;
import java.util.List;

public class Kniha {

	/** Kniha má názov */
	private String nazovDiela;

	/** Kniha má svoje stránky */
	private List<Strana> strany = new ArrayList<Strana>();

	public String getNazovDiela() {
		return nazovDiela;
	}

	public void setNazovDiela(String názovDiela) {
		this.nazovDiela = názovDiela;
	}

	public List<Strana> getStrany() {
		return strany;
	}

	public void setStrany(List<Strana> strany) {
		this.strany = strany;
	}

}
