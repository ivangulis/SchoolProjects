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
	
	/** Inicializácia v konštruktore */
	public Oblecenie(String n, int v, int d, int s){
		this.setNazov(n);
		this.setIDvlastnik(v);
		this.setHodnotaDublony(d);
		this.setHodnotaSerpenty(s);
	}
	
	/** Výpis infa o atribútoch */
	public String zobrazInfo(){
		StringBuilder vypis = new StringBuilder();
		vypis.append("Názov predmetu: " + this.getNazov() + ", ID vlastníka: " + this.getIDvlastnik() 
				+ ", Hodnota v serpentoch: " + this.getHodnotaSerpenty() + ", Hodnota v dublónoch: " + 
				this.getHodnotaDublony() + " a obrana: " + this.getObrana() + "\n");
		return vypis.toString();
	}
}
