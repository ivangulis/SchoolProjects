package model;

import java.util.ArrayList;

public class DatabazaPouzivatelia {
	/** Obsahuje všetkých používate¾ov systému */
	private ArrayList<BeznyPouzivatel> databaza = new ArrayList<BeznyPouzivatel>();
	
	public ArrayList<BeznyPouzivatel> getDatabaza() {
		return databaza;
	}

	public void setDatabaza(ArrayList<BeznyPouzivatel> databaza) {
		this.databaza = databaza;
	}

	/** Inicializácia v konštrukture */
	public DatabazaPouzivatelia(){
		BeznyPouzivatel p1 = new PremiovyPouzivatel("Ivan", "Gulis", 22, databaza.size(), "gulis2505@gmail.com", "Bloodxy" );
		databaza.add(p1);
		BeznyPouzivatel p2 = new PremiovyPouzivatel("Hanz", "Kièinek", 23, databaza.size(), "kokyš123@gmail.com", "Kokys" );
		databaza.add(p2);
	}
	
}
