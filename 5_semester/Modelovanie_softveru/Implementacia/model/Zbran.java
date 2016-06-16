package model;

public class Zbran extends Predmet{
	private double rychlostUtoku = 2.5;
	private int utocnaSila = 100;
	
	public double getRychlostUtoku() {
		return rychlostUtoku;
	}

	public void setRychlostUtoku(double rychlostUtoku) {
		this.rychlostUtoku = rychlostUtoku;
	}

	public int getUtocnaSila() {
		return utocnaSila;
	}

	public void setUtocnaSila(int utocnaSila) {
		this.utocnaSila = utocnaSila;
	}

	public Zbran(){
	}
	
	/** Inicializ�cia v kon�truktore */
	public Zbran(String n, int v, int d, int s){
		this.setNazov(n);
		this.setIDvlastnik(v);
		this.setHodnotaDublony(d);
		this.setHodnotaSerpenty(s);
	}
	
	/** V�pis infa o atrib�toch */
	public String zobrazInfo(){
		StringBuilder vypis = new StringBuilder();
		vypis.append("N�zov predmetu: " + this.getNazov() + ", ID vlastn�ka: " + this.getIDvlastnik() 
				+ ", Hodnota v serpentoch: " + this.getHodnotaSerpenty() + ", Hodnota v dubl�noch: " + 
				this.getHodnotaDublony() + ", r�chlos� �toku: " + rychlostUtoku + " a �to�n� sila: " + utocnaSila + "\n");
				
		return vypis.toString();
	}

}
