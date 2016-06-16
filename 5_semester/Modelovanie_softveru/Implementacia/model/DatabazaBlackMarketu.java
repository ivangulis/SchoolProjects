package model;

import java.util.ArrayList;

public class DatabazaBlackMarketu {
	
	private ArrayList<Predmet> databaza = new ArrayList<Predmet>();
	
	public ArrayList<Predmet> getDatabaza() {
		return databaza;
	}

	public void setDatabaza(ArrayList<Predmet> databaza) {
		this.databaza = databaza;
	}

	/** Inicializácia v konštruktore */
	public DatabazaBlackMarketu(){
		databaza.add(new Zbran("Meè osudu", -1, 13, 150));
		databaza.add(new Oblecenie("Èierny kabát", -1, 15, 100));
		databaza.add(new Zbran("Sekera štastia", -1, 20, 80));
		databaza.add(new Oblecenie("Èiapka lovca", -1, 35, 200));
		databaza.add(new Zbran("Meè lásky", -1, 12, 100));
		databaza.add(new Oblecenie("Strieborný kabát", -1, 5, 50));
		databaza.add(new Zbran("Kyj zabijaka", -1, 80, 250));
		databaza.add(new Zbran("Halapartòa zla", -1, 1, 10));
	}

}
