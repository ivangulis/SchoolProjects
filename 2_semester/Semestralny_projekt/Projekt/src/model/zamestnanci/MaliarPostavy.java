package model.zamestnanci;

import model.prvky.*;

public class MaliarPostavy extends Umelec {
	
/** Maliari vlastnia aj z�sobn�k farby */
	private int farba = 10000;
	
		/** Ka�d� maliar postavy kresl� iba 1 postavi�ku */
	private String menoPostavyMaliara;

	
	private boolean bojovnik = false;
	private boolean remeselnik = false;
	private boolean starec = false;
	private boolean teenager = false;

	public boolean isBojovnik() {
		return bojovnik;
	}

	public void setBojovnik(boolean bojovnik) {
		this.bojovnik = bojovnik;
	}

	public boolean isRemeselnik() {
		return remeselnik;
	}

	public void setRemeselnik(boolean remeselnik) {
		this.remeselnik = remeselnik;
	}

	public boolean isStarec() {
		return starec;
	}

	public void setStarec(boolean starec) {
		this.starec = starec;
	}

	public boolean isTeenager() {
		return teenager;
	}

	public void setTeenager(boolean teenager) {
		this.teenager = teenager;
	}

	public String getMenoPostavyMaliara() {
		return menoPostavyMaliara;
	}

	public void setMenoPostavyMaliara(String menoPostavyMaliara) {
		this.menoPostavyMaliara = menoPostavyMaliara;
	}
	
/** Zni�uje svoju z�sobu atramentu */
	public void znizAtrament() {
		
		this.atrament -= spotrebaAtramentuPostava;
	}
	
/** Zni�uje svoju z�sobu farby */
	public void znizFarbu() {
		
		this.farba -= spotrebaFarbyPostava;
	}
	
/** Zni�uje svoju z�sobu energie */
	public void znizEnergiu() {
		
		this.energia -= spotrebaEnergiePostava;
	}
	
/** Vyp�e svoj stav atrib�tov farba, atrament a energia */
	public String svojStav() {
		
		return ("Zost�va mi u� len " + this.atrament + " atramentu, "
				+ this.farba + " farby a " + this.energia + " energie." + "\n");
	}
	
/** Ka�d� umelec kresl� nie�o in�, tento nakresl� svoju postavi�ku */
	public void nakresli(Kniha komix, int stranaCislo, int panelCislo,
			int postavaCislo, String s) {
		/** Nakresl� bojovn�ka */
		if (this.bojovnik)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Bojovnik();
		/** Nakresl� remeseln�ka */
		if (this.remeselnik)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Remeselnik();
		/** Nakresl� starca */
		if (this.starec)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Starec();
		/** Nakresl� teenagera */
		if (this.teenager)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Teenager();
		
		/** Pomenuje ho */
		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getPostavy()[postavaCislo - 1]
				.setMeno(this.menoPostavyMaliara);
		

		znizAtrament();
		znizFarbu();
		znizEnergiu();
	}
}
