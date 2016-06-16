package model;

public class Predmet {
	private String nazov;
	private int IDvlastnik;
	private int hodnotaDublony = 10;
	private int hodnotaSerpenty = 60;
	
	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public int getIDvlastnik() {
		return IDvlastnik;
	}

	public void setIDvlastnik(int iDvlastnik) {
		IDvlastnik = iDvlastnik;
	}

	public int getHodnotaDublony() {
		return hodnotaDublony;
	}

	public void setHodnotaDublony(int hodnotaDublony) {
		this.hodnotaDublony = hodnotaDublony;
	}

	public int getHodnotaSerpenty() {
		return hodnotaSerpenty;
	}

	public void setHodnotaSerpenty(int hodnotaSerpenty) {
		this.hodnotaSerpenty = hodnotaSerpenty;
	}

	public Predmet(){
	}
	
	/** Inicializácia v konštruktore */
	public Predmet(String n, int v){
		nazov = n;
		IDvlastnik = v;
	}
	
	/** Pre dedenie */
	public String zobrazInfo(){
		return null;}
	

}
