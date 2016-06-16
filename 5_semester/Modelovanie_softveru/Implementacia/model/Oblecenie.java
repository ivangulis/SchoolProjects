package model;

public class Oblecenie extends Predmet {
	private int obrana = 100;
	
	public int getObrana() {
		return obrana;
	}

	public void setObrana(int obrana) {
		this.obrana = obrana;
	}

	public Oblecenie(){
	}
	
	/** Inicializ�cia v kon�truktore */
	public Oblecenie(String n, int v, int d, int s){
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
				this.getHodnotaDublony() + " a obrana: " + this.getObrana() + "\n");
		return vypis.toString();
	}
}
