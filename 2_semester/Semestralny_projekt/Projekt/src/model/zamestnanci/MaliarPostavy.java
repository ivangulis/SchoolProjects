package model.zamestnanci;

import model.prvky.*;

public class MaliarPostavy extends Umelec {
	
/** Maliari vlastnia aj zásobník farby */
	private int farba = 10000;
	
		/** Každý maliar postavy kreslí iba 1 postavièku */
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
	
/** Znižuje svoju zásobu atramentu */
	public void znizAtrament() {
		
		this.atrament -= spotrebaAtramentuPostava;
	}
	
/** Znižuje svoju zásobu farby */
	public void znizFarbu() {
		
		this.farba -= spotrebaFarbyPostava;
	}
	
/** Znižuje svoju zásobu energie */
	public void znizEnergiu() {
		
		this.energia -= spotrebaEnergiePostava;
	}
	
/** Vypíše svoj stav atribútov farba, atrament a energia */
	public String svojStav() {
		
		return ("Zostáva mi už len " + this.atrament + " atramentu, "
				+ this.farba + " farby a " + this.energia + " energie." + "\n");
	}
	
/** Každý umelec kreslí nieèo iné, tento nakreslí svoju postavièku */
	public void nakresli(Kniha komix, int stranaCislo, int panelCislo,
			int postavaCislo, String s) {
		/** Nakreslí bojovníka */
		if (this.bojovnik)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Bojovnik();
		/** Nakreslí remeselníka */
		if (this.remeselnik)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Remeselnik();
		/** Nakreslí starca */
		if (this.starec)
			komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
					.getPostavy()[postavaCislo - 1] = new Starec();
		/** Nakreslí teenagera */
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
