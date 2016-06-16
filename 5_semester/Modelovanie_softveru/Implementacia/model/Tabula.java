package model;

import java.util.ArrayList;

public class Tabula {
	
	/** Obsahuje všetky príspevky v systéme */
	private ArrayList<Prispevok> databaza = new ArrayList<Prispevok>();

	public ArrayList<Prispevok> getDatabaza() {
		return databaza;
	}

	public void setDatabaza(ArrayList<Prispevok> databaza) {
		this.databaza = databaza;
	}

}
