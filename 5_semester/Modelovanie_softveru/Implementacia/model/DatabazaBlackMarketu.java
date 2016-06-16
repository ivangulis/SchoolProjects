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

	/** Inicializ�cia v kon�truktore */
	public DatabazaBlackMarketu(){
		databaza.add(new Zbran("Me� osudu", -1, 13, 150));
		databaza.add(new Oblecenie("�ierny kab�t", -1, 15, 100));
		databaza.add(new Zbran("Sekera �tastia", -1, 20, 80));
		databaza.add(new Oblecenie("�iapka lovca", -1, 35, 200));
		databaza.add(new Zbran("Me� l�sky", -1, 12, 100));
		databaza.add(new Oblecenie("Strieborn� kab�t", -1, 5, 50));
		databaza.add(new Zbran("Kyj zabijaka", -1, 80, 250));
		databaza.add(new Zbran("Halapart�a zla", -1, 1, 10));
	}

}
