package controller;

import java.util.ArrayList;

import model.BeznyPouzivatel;
import model.DatabazaAukcie;
import model.Polozka;
import model.PremiovyPouzivatel;

public class Aukcia implements SpravcaPredmetov {
	/** Singleton */
	private static Aukcia instance = null;
	private Aukcia() {}
	public static Aukcia getInstance() {
		if (instance == null)
			instance = new Aukcia();
		return instance;
	}
	
	/** Ponuka polo�iek v aukcii */
	private ArrayList<Polozka> ponuka = new ArrayList<Polozka>();
	private DatabazaAukcie datPredmety = new DatabazaAukcie();
	
	public ArrayList<Polozka> getPonuka() {
		return ponuka;
	}
	public void setPonuka(ArrayList<Polozka> ponuka) {
		this.ponuka = ponuka;
	}
	public DatabazaAukcie getDatPredmety() {
		return datPredmety;
	}
	public void setDatPredmety(DatabazaAukcie datPredmety) {
		this.datPredmety = datPredmety;
	}
	
	
	/** Od��ta cenu predmetu v dubl�noch od pou��vate�ov�ch dubl�nov */
	public void odcitajDublony(PremiovyPouzivatel kupujuci, int hodnota){
		kupujuci.setDublony(kupujuci.getDublony()-hodnota);
	}


	public void odcitajSerpenty(BeznyPouzivatel kupujuci, int hodnota) {
	}

}
