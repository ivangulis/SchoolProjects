package model;

public class BeznyPouzivatel {
	private String meno;
	private String priezvisko;
	private int vek;
	private int ID;
	private String email;
	private int level = 1;
	private String prezyvka;
	private int serpenty = 500;
	private Inventar inventar;
	
	
	
	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getPriezvisko() {
		return priezvisko;
	}

	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}

	public int getVek() {
		return vek;
	}

	public void setVek(int vek) {
		this.vek = vek;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPrezyvka() {
		return prezyvka;
	}

	public void setPrezyvka(String prezyvka) {
		this.prezyvka = prezyvka;
	}

	public int getSerpenty() {
		return serpenty;
	}

	public void setSerpenty(int serpenty) {
		this.serpenty = serpenty;
	}

	public Inventar getInventar() {
		return inventar;
	}

	public void setInventar(Inventar inventar) {
		this.inventar = inventar;
	}

	public BeznyPouzivatel(){		
	}
	
	/** Inicializácia v konštruktore */
	public BeznyPouzivatel(String m, String p, int v, int i, String e, String prez){	
		meno = m;
		priezvisko = p;
		vek = v;
		ID = i;
		email = e;
		prezyvka = prez;
		inventar = new Inventar(ID);
	}

}
