package model;

public class PremiovyPouzivatel extends BeznyPouzivatel {
	
	private int dublony = 50;
	
	public int getDublony() {
		return dublony;
	}

	public void setDublony(int dublony) {
		this.dublony = dublony;
	}

	public PremiovyPouzivatel(){		
	}
	
	/** Inicializácia v konštruktore */
	public PremiovyPouzivatel(String m, String p, int v, int i, String e, String prez){	
		this.setMeno(m);
		this.setPriezvisko(p);
		this.setVek(v);
		this.setID(i);
		this.setEmail(e);
		this.setPrezyvka(prez);
		this.setInventar(new Inventar(this.getID()));
	}

}
