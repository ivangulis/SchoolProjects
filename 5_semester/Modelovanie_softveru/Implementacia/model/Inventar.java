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
	/** Inicializ�cia invent�ra */
	public Inventar(int id){		
		predmety.add(new Zbran("Me� osudu", id, 10, 100));
		predmety.add(new Oblecenie("�ierny kabat", id, 15, 200));
		predmety.add(new Zbran("Sekera �tastia", id, 30, 40));
		predmety.add(new Oblecenie("�iapka lovca", id, 12, 150));
	}
	
	/** Zobraz� v�etky predmety (=vr�ti pole pre combobox) */
	public String[] vsetkyPredmety() {
		ArrayList<String> celyVypis = new ArrayList<String>();
		for (Predmet o : predmety)
		 celyVypis.add(o.getNazov());
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)//tvor�m pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/** N�jde predmet pod�a n�zvu */
	public Predmet n�jdiPredmet(String nazov){
		for (Predmet p : predmety)
			 if (nazov.equals(p.getNazov()))
				 return p;
		return null;
	}
}
