package model;

import java.util.ArrayList;

public class DatabazaPouzivatelia {
	/** Obsahuje v�etk�ch pou��vate�ov syst�mu */
	private ArrayList<BeznyPouzivatel> databaza = new ArrayList<BeznyPouzivatel>();
	
	public ArrayList<BeznyPouzivatel> getDatabaza() {
		return databaza;
	}

	public void setDatabaza(ArrayList<BeznyPouzivatel> databaza) {
		this.databaza = databaza;
	}

	/** Inicializ�cia v kon�trukture */
	public DatabazaPouzivatelia(){
		BeznyPouzivatel p1 = new PremiovyPouzivatel("Ivan", "Gulis", 22, databaza.size(), "gulis2505@gmail.com", "Bloodxy" );
		databaza.add(p1);
		BeznyPouzivatel p2 = new PremiovyPouzivatel("Hanz", "Ki�inek", 23, databaza.size(), "koky�123@gmail.com", "Kokys" );
		databaza.add(p2);
	}
	
}
