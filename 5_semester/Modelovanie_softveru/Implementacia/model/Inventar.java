package model;

import java.util.ArrayList;

public class Inventar {

	private ArrayList<Predmet> predmety = new ArrayList<Predmet>();
	
	public ArrayList<Predmet> getPredmety() {
		return predmety;
	}
	public void setPredmety(ArrayList<Predmet> predmety) {
		this.predmety = predmety;
	}
	public Inventar(){
	}
	/** Inicializácia inventára */
	public Inventar(int id){		
		predmety.add(new Zbran("Meè osudu", id, 10, 100));
		predmety.add(new Oblecenie("Èierny kabat", id, 15, 200));
		predmety.add(new Zbran("Sekera štastia", id, 30, 40));
		predmety.add(new Oblecenie("Èiapka lovca", id, 12, 150));
	}
	
	/** Zobrazí všetky predmety (=vráti pole pre combobox) */
	public String[] vsetkyPredmety() {
		ArrayList<String> celyVypis = new ArrayList<String>();
		for (Predmet o : predmety)
		 celyVypis.add(o.getNazov());
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)//tvorím pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/** Nájde predmet pod¾a názvu */
	public Predmet nájdiPredmet(String nazov){
		for (Predmet p : predmety)
			 if (nazov.equals(p.getNazov()))
				 return p;
		return null;
	}
}
