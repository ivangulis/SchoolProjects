package model.zamestnanci;

import model.prvky.*;

public class MaliarPozadia extends Umelec {

	/** Maliari vlastnia aj z�sobn�k farby */
	private int farba = 10000;

	/** Zni�uje svoju z�sobu atramentu */
	public void znizAtrament() {
		this.atrament -= spotrebaAtramentuPozadie;
	}

	/** Zni�uje svoju z�sobu farby */
	public void znizFarbu() {

		this.farba -= spotrebaFarbyPozadie;
	}

	/** Zni�uje svoju z�sobu energie */
	public void znizEnergiu() {
		this.energia -= spotrebaEnergiePozadie;
	}

	/** Vyp�e svoj stav atrib�tov farba, atrament a energia */
	public String svojStav() {
		return ("Zost�va mi u� len " + this.atrament + " atramentu, "
				+ this.farba + " farby a " + this.energia + " energie." + "\n");
	}

	/** Ka�d� umelec kresl� nie�o in�, tento nakresl� nejak� pozadie */
	public void nakresli(Kniha komix, int stranaCislo, int panelCislo, int i,
			String s) {

		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getPozadie().setNazov(s);
		znizAtrament();
		znizFarbu();
		znizEnergiu();
	}
}
