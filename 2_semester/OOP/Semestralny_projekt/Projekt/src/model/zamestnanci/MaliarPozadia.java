package model.zamestnanci;

import model.prvky.*;

public class MaliarPozadia extends Umelec {

	/** Maliari vlastnia aj zásobník farby */
	private int farba = 10000;

	/** Znižuje svoju zásobu atramentu */
	public void znizAtrament() {
		this.atrament -= spotrebaAtramentuPozadie;
	}

	/** Znižuje svoju zásobu farby */
	public void znizFarbu() {

		this.farba -= spotrebaFarbyPozadie;
	}

	/** Znižuje svoju zásobu energie */
	public void znizEnergiu() {
		this.energia -= spotrebaEnergiePozadie;
	}

	/** Vypíše svoj stav atribútov farba, atrament a energia */
	public String svojStav() {
		return ("Zostáva mi už len " + this.atrament + " atramentu, "
				+ this.farba + " farby a " + this.energia + " energie." + "\n");
	}

	/** Každý umelec kreslí nieèo iné, tento nakreslí nejaké pozadie */
	public void nakresli(Kniha komix, int stranaCislo, int panelCislo, int i,
			String s) {

		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getPozadie().setNazov(s);
		znizAtrament();
		znizFarbu();
		znizEnergiu();
	}
}
